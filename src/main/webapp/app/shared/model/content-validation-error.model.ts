import { ITask } from 'app/shared/model/task.model';
import { ITaskExecution } from 'app/shared/model/task-execution.model';

export interface IContentValidationError {
  id?: number;
  sourceIndex?: number;
  sourceText?: string;
  errorText?: string;
  task?: ITask;
  taskExecution?: ITaskExecution;
}

export class ContentValidationError implements IContentValidationError {
  constructor(
    public id?: number,
    public sourceIndex?: number,
    public sourceText?: string,
    public errorText?: string,
    public task?: ITask,
    public taskExecution?: ITaskExecution
  ) {}
}
