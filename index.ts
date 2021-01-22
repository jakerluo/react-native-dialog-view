import { NativeModules } from "react-native";

const {
  ReactNativeDialogView,
  ReactNativeProgress,
  ReactNativeAlert,
} = NativeModules;

export const DialogProgress = ReactNativeProgress;
export const DialogAlert = ReactNativeAlert;
export default ReactNativeDialogView;
