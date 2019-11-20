import { SimpleOrSketchSpec } from '../../api/component/SpecTypes';
import { ToolbarGroupSpec } from '../types/ToolbarGroupTypes';
import { SplitToolbarBaseDetail, SplitToolbarBaseSpec, SplitToolbarBaseSketcher } from './SplitToolbarBaseTypes';

export interface SplitSlidingToolbarDetail extends SplitToolbarBaseDetail {
  markers: {
    closedClass: string;
    openClass: string;
    shrinkingClass: string;
    growingClass: string;
    overflowToggledClass: string;
  };
}

export interface SplitSlidingToolbarSpec extends SplitToolbarBaseSpec {
  markers: {
    closedClass: string;
    openClass: string;
    shrinkingClass: string;
    growingClass: string;
    overflowToggledClass: string;
  };

  parts: {
    'overflow-group': Partial<ToolbarGroupSpec>,
    'overflow-button': Partial<SimpleOrSketchSpec>
  };
}

export interface SplitSlidingToolbarSketcher extends SplitToolbarBaseSketcher<SplitSlidingToolbarSpec, SplitSlidingToolbarDetail> { }
