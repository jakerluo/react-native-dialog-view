import { NativeModules } from "react-native";

const { ReactNativeDialogView, ReactNativeProgress } = NativeModules;

export const DialogProgress = ReactNativeProgress;
export default ReactNativeDialogView;
