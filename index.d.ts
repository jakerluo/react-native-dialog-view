export type DialogProgressShowOptions = {
  title?: string;
  message?: string;
  isCancelable?: boolean;
};

declare namespace DialogProgress {
  function show(
    dialogProgressShowOptions: DialogProgressShowOptions
  ): Promise<boolean | string>;

  function hide(): Promise<boolean>;

  function changeMessage(message: string): Promise<boolean>;
}

declare namespace DialogAlert {
  function show(): Promise<boolean | string>;
}

export { DialogProgress, DialogAlert };
