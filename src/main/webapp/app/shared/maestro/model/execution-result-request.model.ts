export class ExecutionResultRequest {
  taskExecutionId?: number;
  contentOnly: boolean;
  constructor() {
    this.contentOnly = false;
  }
}
