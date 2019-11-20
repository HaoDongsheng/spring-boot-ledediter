/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 */

import { AlloyTriggers, AlloyComponent } from '@ephox/alloy';
import { Element } from '@ephox/dom-globals';
import { Option } from '@ephox/katamari';
import Editor from 'tinymce/core/api/Editor';
import { updateMenuText } from '../../dropdown/CommonDropdown';
import { onActionToggleFormat } from './utils/Utils';
import { createMenuItems, createSelectButton, SelectSpec } from './BespokeSelect';
import { buildBasicSettingsDataset, Delimiter } from './SelectDatasets';
import { findNearest, getCurrentSelectionParents } from './utils/FormatDetection';

const defaultBlocks = (
  'Paragraph=p;' +
  'Heading 1=h1;' +
  'Heading 2=h2;' +
  'Heading 3=h3;' +
  'Heading 4=h4;' +
  'Heading 5=h5;' +
  'Heading 6=h6;' +
  'Preformatted=pre'
);

const getSpec = (editor): SelectSpec & { dataset } => {
  const getMatchingValue = (nodeChangeEvent) => {
    return findNearest(editor, () => dataset.data, nodeChangeEvent);
  };

  const isSelectedFor = (format) => {
    return () => {
      return editor.formatter.match(format);
    };
  };

  const getPreviewFor = (format) => () => {
    const fmt = editor.formatter.get(format);
    return Option.some({
      tag: fmt.length > 0 ? fmt[0].inline || fmt[0].block || 'div' : 'div',
      styleAttr: editor.formatter.getCssText(format)
    });
  };

  const updateSelectMenuText = (parents: Element[], comp: AlloyComponent) => {
    const detectedFormat = getMatchingValue(parents);
    const text = detectedFormat.fold(() => 'Paragraph', (fmt) => fmt.title);
    AlloyTriggers.emitWith(comp, updateMenuText, {
      text
    });
  };

  const nodeChangeHandler = Option.some((comp: AlloyComponent) => {
    return (e) => updateSelectMenuText(e.parents, comp);
  });

  const setInitialValue = Option.some((comp: AlloyComponent) => {
    const parents = getCurrentSelectionParents(editor);
    updateSelectMenuText(parents, comp);
  });

  const dataset = buildBasicSettingsDataset(editor, 'block_formats', defaultBlocks, Delimiter.SemiColon);

  return {
    tooltip: 'Blocks',
    icon: Option.none(),
    isSelectedFor,
    getPreviewFor,
    onAction: onActionToggleFormat(editor),
    setInitialValue,
    nodeChangeHandler,
    dataset,
    shouldHide: false,
    isInvalid: (item) => !editor.formatter.canApply(item.format)
  };
};

const createFormatSelect = (editor: Editor, backstage) => {
  const spec = getSpec(editor);
  return createSelectButton(editor, backstage, spec.dataset, spec);
};

// FIX: Test this!
const formatSelectMenu = (editor: Editor, backstage) => {
  const spec = getSpec(editor);
  const menuItems = createMenuItems(editor, backstage, spec.dataset, spec);
  editor.ui.registry.addNestedMenuItem('blockformats', {
    text: 'Blocks',
    getSubmenuItems: () => menuItems.items.validateItems(menuItems.getStyleItems())
  });
};

export { createFormatSelect, formatSelectMenu };
