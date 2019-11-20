import {
  AlloyEvents,
  DomFactory,
  GuiFactory,
  Input as AlloyInput,
  Memento,
  Representing,
  SimpleSpec,
} from '@ephox/alloy';
import { Menu, Types } from '@ephox/bridge';
import { ValueSchema } from '@ephox/boulder';
import { console } from '@ephox/dom-globals';
import { Arr, Id, Option } from '@ephox/katamari';
import { renderAlertBanner } from 'tinymce/themes/silver/ui/general/AlertBanner';

import { renderAutocomplete } from 'tinymce/themes/silver/ui/dialog/Autocomplete';
import { renderBodyPanel } from 'tinymce/themes/silver/ui/dialog/BodyPanel';
import { renderColorInput } from 'tinymce/themes/silver/ui/dialog/ColorInput';
import { renderColorPicker } from 'tinymce/themes/silver/ui/dialog/ColorPicker';
import { renderCustomEditor } from 'tinymce/themes/silver/ui/dialog/CustomEditor';
import { renderDropZone } from 'tinymce/themes/silver/ui/dialog/Dropzone';
import { renderGrid } from 'tinymce/themes/silver/ui/dialog/Grid';
import { renderIFrame } from 'tinymce/themes/silver/ui/dialog/IFrame';
import { renderSelectBox } from 'tinymce/themes/silver/ui/dialog/SelectBox';
import { renderSizeInput } from 'tinymce/themes/silver/ui/dialog/SizeInput';
import { renderInput, renderTextarea } from 'tinymce/themes/silver/ui/dialog/TextField';
import { renderTypeahead } from 'tinymce/themes/silver/ui/dialog/TypeAheadInput';
import { renderUrlInput } from 'tinymce/themes/silver/ui/dialog/UrlInput';
import { renderButton } from 'tinymce/themes/silver/ui/general/Button';
import { renderListbox } from 'tinymce/themes/silver/ui/general/Listbox';
import { UiFactoryBackstageShared, UiFactoryBackstage } from 'tinymce/themes/silver/backstage/Backstage';
import { renderLabel } from 'tinymce/themes/silver/ui/dialog/Label';
import { renderCollection } from 'tinymce/themes/silver/ui/dialog/Collection';
import { renderCheckbox } from 'tinymce/themes/silver/ui/general/Checkbox';
import { setupDemo } from './DemoHelpers';
import Promise from 'tinymce/core/api/util/Promise';

// tslint:disable:no-console

export default () => {
  const helpers = setupDemo();

  const backstage: UiFactoryBackstage = helpers.extras.backstage;
  const sharedBackstage: UiFactoryBackstageShared = {
    getSink: helpers.extras.backstage.shared.getSink,
    providers: helpers.extras.backstage.shared.providers,
    interpreter: (x) => x
  };

  const autocompleteSpec = renderAutocomplete({
    name: 'alpha',
    initialValue: '',
    label: Option.some('Alpha'),
    getItems: (value) => {
      return Arr.map([
        {
          text: 'Cat'
        },
        {
          text: 'Dog'
        }
      ], (d) => makeItem(d.text));
    }
  }, backstage);

  const iframeSpec = renderIFrame({
    type: 'iframe',
    name: 'iframe',
    label: Option.some('Iframe'),
    sandboxed: true
  }, sharedBackstage.providers);

  const inputSpec = renderInput({
    name: 'input',
    label: Option.some('Beta'),
    placeholder: Option.none(),
    validation: Option.some({
      validator: (s) => s === 'bad' ? 'Bad' : true
    })
  }, sharedBackstage.providers);

  const textareaSpec = renderTextarea({
    name: 'textarea',
    label: Option.some('Gamma'),
    placeholder: Option.none(),
    validation: Option.some({
      validator: (s) => s === 'so bad' ? 'So bad' : true
    })
  }, sharedBackstage.providers);

  const makeItem = (text: string): Menu.MenuItemApi => {
    return {
      type: 'menuitem',
      value: Id.generate('item'),
      text,
      onAction: () => console.log('clicked: ' + text)
    };
  };

  const labelSpec = renderLabel({
    label: 'A label wraps components in a group',
    type: 'label',
    items: [
      renderCheckbox({
        label: 'check box item 1',
        name: 'one'
      }, sharedBackstage.providers) as any,
      renderCheckbox({
        label: 'check box item 2',
        name: 'two'
      }, sharedBackstage.providers) as any,
      renderInput({
        label: Option.some('Sample input'),
        placeholder: Option.none(),
        name: 'exampleinputfieldname',
        validation: Option.none()
      }, sharedBackstage.providers) as any
    ]
  }, sharedBackstage);

  const labelGridSpec = renderLabel({
    label: 'A label wraps a grid compontent',
    type: 'label',
    items: [
      renderGrid({
        type: 'grid',
        columns: 2,
        items: [
          renderButton({
            name: 'gridspecbutton',
            text: 'Click Me!',
            primary: false
          }, () => {
            console.log('clicked on the button in the grid wrapped by a label');
          }, sharedBackstage.providers) as any,
          renderCheckbox({
            label: 'check box item 1',
            name: 'one'
          }, sharedBackstage.providers) as any,
          renderCheckbox({
            label: 'check box item 2',
            name: 'two'
          }, sharedBackstage.providers) as any,
          renderInput({
            label: Option.some('Sample input'),
            placeholder: Option.none(),
            name: 'exampleinputfieldname',
            validation: Option.none()
          }, sharedBackstage.providers) as any
        ]
      }, sharedBackstage) as any
    ]
  }, sharedBackstage);

  const listboxSpec = renderListbox({
    name: 'listbox1',
    label: 'Listbox',
    values: [
      { value: 'alpha', text: 'Alpha' },
      { value: 'beta', text: 'Beta' },
      { value: 'gamma', text: 'Gamma' }
    ],
    initialValue: Option.some('beta')
  }, sharedBackstage.providers);

  const gridSpec = renderGrid({
    type: 'grid',
    columns: 5,
    items: [
      AlloyInput.sketch({ inputAttributes: { placeholder: 'Text goes here...' } }) as any,
      renderButton({
        name: 'gridspecbutton',
        text: 'Click Me!',
        primary: false
      }, () => {
        console.log('clicked on the button in the grid');
      }, sharedBackstage.providers) as any
    ]
  }, sharedBackstage);

  const buttonSpec = renderButton({
    name: 'button1',
    text: 'Text',
    primary: false
  }, () => {
    console.log('clicked on the button');
  }, sharedBackstage.providers);

  const checkboxSpec = (() => {
    const memBodyPanel = Memento.record(
      renderBodyPanel({
        items: [
          { type: 'checkbox', name: 'checked', label: 'Checked' },
          { type: 'checkbox', name: 'unchecked', label: 'Unchecked' }
        ]
      }, {
        shared: sharedBackstage
      })
    );

    return {
      dom: {
        tag: 'div'
      },
      components: [
        memBodyPanel.asSpec(),
      ],
      events: AlloyEvents.derive([
        AlloyEvents.runOnAttached((component) => {
          const body = memBodyPanel.get(component);
          Representing.setValue(body, {
            checked: true,
            unchecked: false
          });
        })
      ])
    };
  })();

  const colorInputSpec = renderColorInput({
    type: 'colorinput',
    name: 'colorinput-demo',
    label: Option.some('Color input label')
  }, sharedBackstage, backstage.colorinput);

  const colorPickerSpec = renderColorPicker({
    type: 'colorpicker',
    name: 'colorpicker-demo',
    label: Option.some('Color picker label')
  });

  const dropzoneSpec = renderDropZone({
    type: 'dropzone',
    name: 'dropzone-demo',
    label: Option.some('Dropzone label')
  }, sharedBackstage.providers);

  const selectBoxSpec = renderSelectBox({
    type: 'selectbox',
    name: 'selectbox-demo',
    size: 1,
    label: Option.some('Select one from'),
    items: [
      { value: 'one', text: 'One' },
      { value: 'two', text: 'Two' }
    ]
  }, sharedBackstage.providers);

  const selectBoxSizeSpec = renderSelectBox({
    type: 'selectbox',
    name: 'selectbox-demo',
    size: 6,
    label: Option.some('Select one from'),
    items: [
      { value: 'one', text: 'One' },
      { value: 'two', text: 'Two' },
      { value: 'three', text: 'Three' },
      { value: 'four', text: 'Four' },
      { value: 'five', text: 'Five' },
      { value: 'six', text: 'Six' }
    ]
  }, sharedBackstage.providers);

  const sizeInputSpec = renderSizeInput({
    constrain: true,
    type: 'sizeinput',
    name: 'sizeinput-demo',
    label: Option.some('kustom fixed ratio'),
  }, sharedBackstage.providers);

  const urlInputSpec = renderUrlInput({
    type: 'urlinput',
    name: 'blah',
    label: Option.some('Url'),
    filetype: 'image' // 'image' || 'media'
  }, backstage, backstage.urlinput);

  const linkInputSpec = renderTypeahead({
    label: Option.some('Url'),
    name: 'linkInput',
    icon: 'embed',
    initialValue: '',
    getItems: (value) => {
      return Arr.map([
        {
          value: 'https://www.tinymce.com',
          text: 'My page 1'
        },
        {
          value: 'https://www.ephox.com',
          text: 'My page 2'
        }
      ], (d) => makeItem(d.text));
    }
  }, backstage);

  const customEditorSpec = renderCustomEditor({
      tag: 'textarea',
      init: (el) => new Promise((resolve) => {
        const oldEl = el;
        const newEl = el.ownerDocument.createElement('span');
        newEl.innerText = 'this is a custom editor';
        el.parentElement.replaceChild(newEl, oldEl);

        const api = {
          getValue: () => newEl.innerText,
          setValue: (value) => {
            newEl.innerText = value;
          },
          destroy: () => { newEl.parentElement.replaceChild(oldEl, newEl); }        };

        resolve(api);
      }),
    });

  const alertBannerSpec = renderAlertBanner({
    text: 'The alert banner message',
    level: 'warn',
    icon: 'close',
    actionLabel: 'Click here For somthing'
  }, sharedBackstage.providers);

  const display = (label: string, spec: SimpleSpec) => {
    return {
      dom: {
        tag: 'div',
        styles: { border: '1px solid #aaa', margin: '1em', padding: '1em' }
      },
      components: [
        { dom: DomFactory.fromHtml('<h3>' + label + '</h3>') },
        { dom: { tag: 'hr' } },
        spec
      ]
    };
  };

  const memCollection = Memento.record(
    renderCollection({
      type: 'collection',
      columns: 1,
      name: 'collection',
      label: Option.some('Collection: '),
    }, sharedBackstage.providers)
  );

  const everything = GuiFactory.build({
    dom: {
      tag: 'div'
    },
    components: [
      display('Collection', memCollection.asSpec()),
      display('Dropzone', dropzoneSpec),
      display('UrlInput', urlInputSpec),
      display('LinkTypeAheadInput', linkInputSpec),
      display('SizeInput', sizeInputSpec),
      display('SelectBox', selectBoxSpec),
      display('SelectBox with Size', selectBoxSizeSpec),
      display('Grid', gridSpec),
      display('ColorPicker', colorPickerSpec),
      display('ColorInput', colorInputSpec),
      display('Checkbox', checkboxSpec),
      display('Button', buttonSpec),
      display('Listbox', listboxSpec),
      display('Label', labelSpec),
      display('Grid Label', labelGridSpec),
      display('Autocomplete', autocompleteSpec),
      display('IFrame', iframeSpec),
      display('Input', inputSpec),
      display('Textarea', textareaSpec),
      display('CodeView', customEditorSpec),
      display('AlertBanner', alertBannerSpec)
    ]
  });

  helpers.uiMothership.add(everything);
  memCollection.getOpt(everything).each((collection) => {
    Representing.setValue(collection,
      ValueSchema.asRawOrDie('dialogComponentsDemo.collection', Types.Collection.collectionDataProcessor, [
        {
          value: 'a',
          text: 'A',
          icon: 'a'
        },
        {
          value: 'b',
          text: 'B',
          icon: 'b'
        },
        {
          value: 'c',
          text: 'C',
          icon: 'c'
        },
        {
          value: 'd',
          text: 'D',
          icon: 'd'
        }
      ])
    );
  });
};
