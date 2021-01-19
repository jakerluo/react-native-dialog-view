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
};

export type ConfirmCallback = () => void;

export type CancelCallback = () => void;

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
}

export { DialogProgress, DialogAlert };
