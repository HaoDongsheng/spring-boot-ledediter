/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 */

import { AddEventsBehaviour, AlloyEvents, AlloySpec, AlloyTriggers, Behaviour, Bubble, GuiFactory, InlineView, Keying, Layout, Positioning, MaxHeight } from '@ephox/alloy';
import { Objects } from '@ephox/boulder';
import { Toolbar } from '@ephox/bridge';
import { Element as DomElement } from '@ephox/dom-globals';
import { Cell, Id, Merger, Option, Thunk, Result } from '@ephox/katamari';
import { Css, DomEvent, Element, Focus } from '@ephox/sugar';
import Editor from 'tinymce/core/api/Editor';
import Delay from 'tinymce/core/api/util/Delay';
import { showContextToolbarEvent } from './ui/context/ContextEditorEvents';
import { ContextForm } from './ui/context/ContextForm';
import { forwardSlideEvent, renderContextToolbar } from './ui/context/ContextUi';
import ToolbarLookup from './ui/context/ToolbarLookup';
import ToolbarScopes, { ScopedToolbars } from './ui/context/ToolbarScopes';
import { renderToolbar } from './ui/toolbar/CommonToolbar';
import { identifyButtons } from './ui/toolbar/Integration';

const register = (editor: Editor, registryContextToolbars, sink, extras) => {
  const contextbar = GuiFactory.build(
    renderContextToolbar({
      sink,
      onEscape: () => {
        editor.focus();
        return Option.some(true);
      }
    })
  );

  const getBoxElement = () => Option.some(Element.fromDom(editor.contentAreaContainer));

  editor.on('init', () => {
    const scroller = editor.getBody().ownerDocument.defaultView;

    // FIX: make a lot nicer.
    const onScroll = DomEvent.bind(Element.fromDom(scroller), 'scroll', () => {
      lastAnchor.get().each((anchor) => {
        const elem = lastElement.get().getOr(editor.selection.getNode());
        const nodeBounds = elem.getBoundingClientRect();
        const contentAreaBounds = editor.contentAreaContainer.getBoundingClientRect();
        const aboveEditor = nodeBounds.bottom < 0;
        const belowEditor = nodeBounds.top > contentAreaBounds.height;
        if (aboveEditor || belowEditor) {
          Css.set(contextbar.element(), 'display', 'none');
        } else {
          Css.remove(contextbar.element(), 'display');
          Positioning.positionWithin(sink, anchor, contextbar, getBoxElement());
        }
      });
    });

    editor.on('remove', () => {
      onScroll.unbind();
    });
  });

  const lastAnchor = Cell(Option.none());
  const lastElement = Cell<Option<DomElement>>(Option.none<DomElement>());
  const timer = Cell(null);

  const wrapInPopDialog = (toolbarSpec: AlloySpec) => {
    return {
      dom: {
        tag: 'div',
        classes: ['tox-pop__dialog'],
      },
      components: [toolbarSpec],
      behaviours: Behaviour.derive([
        Keying.config({
          mode: 'acyclic'
        }),

        AddEventsBehaviour.config('pop-dialog-wrap-events', [
          AlloyEvents.runOnAttached((comp) => {
            editor.shortcuts.add('ctrl+F9', 'focus statusbar', () => Keying.focusIn(comp));
          }),
          AlloyEvents.runOnDetached((comp) => {
            editor.shortcuts.remove('ctrl+F9');
          })
        ])
      ])
    };
  };

  const getScopes: () => ScopedToolbars = Thunk.cached(() => {
    return ToolbarScopes.categorise(registryContextToolbars, (toolbarApi) => {
      const alloySpec = buildToolbar(toolbarApi);
      AlloyTriggers.emitWith(contextbar, forwardSlideEvent, {
        forwardContents: wrapInPopDialog(alloySpec)
      });
    });
  });

  const buildToolbar = (ctx): AlloySpec => {
    const { buttons } = editor.ui.registry.getAll();

    const scopes = getScopes();
    return ctx.type === 'contexttoolbar' ? (() => {
      const allButtons = Merger.merge(buttons, scopes.formNavigators);
      const initGroups = identifyButtons(editor, { buttons: allButtons, toolbar: ctx.items }, extras, Option.some([ 'form:' ]));
      return renderToolbar({
        uid: Id.generate('context-toolbar'),
        initGroups,
        onEscape: Option.none,
        cyclicKeying: true,
        backstage: extras.backstage,
        getSink: () => Result.error('')
      });
    })() : (() => {
      return ContextForm.renderContextForm(ctx, extras.backstage);
    })();
  };

  editor.on(showContextToolbarEvent, (e) => {
    const scopes = getScopes();
    // TODO: Have this stored in a better structure
    Objects.readOptFrom<Toolbar.ContextToolbar | Toolbar.ContextForm>(scopes.lookupTable, e.toolbarKey).each((ctx) => {
      launchContext(ctx, e.target === editor ? Option.none() : Option.some(e as DomElement));
      // Forms launched via this way get immediate focus
      InlineView.getContent(contextbar).each(Keying.focusIn);
    });
  });

  const bubbleAlignments = {
    valignCentre: [],
    alignCentre: [],
    alignLeft: ['tox-pop--align-left'],
    alignRight: ['tox-pop--align-right'],
    right: ['tox-pop--right'],
    left: ['tox-pop--left'],
    bottom: ['tox-pop--bottom'],
    top: ['tox-pop--top']
  };

  const anchorOverrides = {
    maxHeightFunction: MaxHeight.expandable()
  };

  const lineAnchorSpec = {
    bubble: Bubble.nu(12, 0, bubbleAlignments),
    layouts: {
      onLtr: () => [Layout.east],
      onRtl: () => [Layout.west]
    },
    overrides: anchorOverrides
  };

  const anchorSpec = {
    bubble: Bubble.nu(0, 12, bubbleAlignments),
    layouts: {
      onLtr: () => [Layout.north, Layout.south, Layout.northeast, Layout.southeast, Layout.northwest, Layout.southwest],
      onRtl: () => [Layout.north, Layout.south, Layout.northwest, Layout.southwest, Layout.northeast, Layout.southeast]
    },
    overrides: anchorOverrides
  };

  const getAnchor = (position: Toolbar.ContextToolbarPosition, element: Option<Element>) => {
    const anchorage = position === 'node' ? extras.backstage.shared.anchors.node(element) : extras.backstage.shared.anchors.cursor();
    const anchor = Merger.deepMerge(
      anchorage,
      position === 'line' ? lineAnchorSpec : anchorSpec
    );
    return anchor;
  };

  const launchContext = (toolbarApi: Toolbar.ContextToolbar | Toolbar.ContextForm, elem: Option<DomElement>) => {
    clearTimer();
    const toolbarSpec = buildToolbar(toolbarApi);
    const sElem = elem.map(Element.fromDom);
    const anchor = getAnchor(toolbarApi.position, sElem);
    lastAnchor.set(Option.some((anchor)));
    lastElement.set(elem);
    InlineView.showWithin(contextbar, anchor, wrapInPopDialog(toolbarSpec), getBoxElement());
    Css.remove(contextbar.element(), 'display');
  };

  const launchContextToolbar = () => {
    const scopes = getScopes();
    ToolbarLookup.lookup(scopes, editor).fold(
      () => {
        lastAnchor.set(Option.none());
        InlineView.hide(contextbar);
      },

      (info) => {
        launchContext(info.toolbarApi, Option.some(info.elem.dom()));
      }
    );
  };

  const clearTimer = () => {
    const current = timer.get();
    if (current !== null) {
      Delay.clearTimeout(current);
      timer.set(null);
    }
  };

  const resetTimer = (t) => {
    clearTimer();
    timer.set(t);
  };

  editor.on('init', () => {
    // FIX: Make it go away when the action makes it go away. E.g. deleting a column deletes the table.
    editor.on('click keyup SetContent ObjectResized ResizeEditor', (e) => {
      // Fixing issue with chrome focus on img.
      resetTimer(
        Delay.setEditorTimeout(editor, launchContextToolbar, 0)
      );
    });

    editor.on('focusout', (e) => {
      Delay.setEditorTimeout(editor, () => {
        if (Focus.search(sink.element()).isNone() && Focus.search(contextbar.element()).isNone()) {
          lastAnchor.set(Option.none());
          InlineView.hide(contextbar);
        }
      }, 0);
    });

    editor.on('SwitchMode', () => {
      if (editor.readonly) {
        lastAnchor.set(Option.none());
        InlineView.hide(contextbar);
      }
    });

    editor.on('NodeChange', (e) => {
      Focus.search(contextbar.element()).fold(
        () => {
          resetTimer(
            Delay.setEditorTimeout(editor, launchContextToolbar, 0)
          );
        },
        (_) => {

        }
      );
    });
  });
};

export default {
  register
};