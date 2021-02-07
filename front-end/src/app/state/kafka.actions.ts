export class AddMessageAction {
  static readonly type = 'kafkaStream';
  constructor(public message: string) { }
}
