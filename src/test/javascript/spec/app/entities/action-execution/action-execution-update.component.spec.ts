import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionExecutionUpdateComponent } from 'app/entities/action-execution/action-execution-update.component';
import { ActionExecutionService } from 'app/entities/action-execution/action-execution.service';
import { ActionExecution } from 'app/shared/model/action-execution.model';

describe('Component Tests', () => {
  describe('ActionExecution Management Update Component', () => {
    let comp: ActionExecutionUpdateComponent;
    let fixture: ComponentFixture<ActionExecutionUpdateComponent>;
    let service: ActionExecutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionExecutionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActionExecutionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionExecutionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActionExecutionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActionExecution(123);
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
        const entity = new ActionExecution();
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
