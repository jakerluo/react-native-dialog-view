export type DialogProgressShowOptions = {
  title?: string;
  message?: string;
  isCancelable?: boolean;
};

export type DialogAlertShowOptions = {
  title?: string;
  message?: string;
  cancelable?: boolean;
  cancelText?: string;
  okText?: string;
  minusText?: string;
  additionText?: string;
  descText?: string;
  initialNum?: number;
  showInput?: boolean;
  showTitleiIcon?: boolean;
  customTemplate?: boolean;
};

export type ConfirmCallback = (nums: number) => void;

export type CancelCallback = (nums: number) => void;

declare namespace DialogProgress {
  function show(
    dialogProgressShowOptions: DialogProgressShowOptions
  ): Promise<boolean | string>;

  function hide(): Promise<boolean>;

  function changeMessage(message: string): Promise<boolean>;
}

declare namespace DialogAlert {
  function show(
    dialogAlertShowOptions: DialogAlertShowOptions,
    confirmCallback: ConfirmCallback,
    cancelCallback: CancelCallback
  ): void;
  function hide(): Promise<boolean>;
}

export { DialogProgress, DialogAlert };
