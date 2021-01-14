import { NativeModules } from "react-native";

const { ReactNativeDialogView, ReactNativeProgress } = NativeModules;

ReactNativeDialogView.ReactNativeProgress = ReactNativeProgress;

export default ReactNativeDialogView;
