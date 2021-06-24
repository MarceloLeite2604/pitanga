export enum ToastType {
  INFORMATION = 'info',
  SUCCESS = 'success',
  WARNING = 'warn',
  ERROR = 'error'
}

export interface Toast {
  message: string,
  type?: ToastType
}
