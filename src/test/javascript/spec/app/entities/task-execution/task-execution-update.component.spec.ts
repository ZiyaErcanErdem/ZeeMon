import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { TaskExecutionUpdateComponent } from 'app/entities/task-execution/task-execution-update.component';
import { TaskExecutionService } from 'app/entities/task-execution/task-execution.service';
import { TaskExecution } from 'app/shared/model/task-execution.model';

describe('Component Tests', () => {
  describe('TaskExecution Management Update Component', () => {
    let comp: TaskExecutionUpdateComponent;
    let fixture: ComponentFixture<TaskExecutionUpdateComponent>;
    let service: TaskExecutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [TaskExecutionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TaskExecutionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskExecutionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskExecutionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskExecution(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskExecution();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
