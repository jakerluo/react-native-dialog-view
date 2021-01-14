export type DialogProgressShowOptions = {
  title?: string;
  message?: string;
  isCancelable?: boolean;
};

declare class DialogProgress {
  show(
    dialogProgressShowOptions: DialogProgressShowOptions
  ): Promise<boolean | string>;
  hide(): Promise<boolean>;
  changeMessage(message: string): Promise<boolean>;
}

export { DialogProgress };
