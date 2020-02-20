import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { FlowExecutionUpdateComponent } from 'app/entities/flow-execution/flow-execution-update.component';
import { FlowExecutionService } from 'app/entities/flow-execution/flow-execution.service';
import { FlowExecution } from 'app/shared/model/flow-execution.model';

describe('Component Tests', () => {
  describe('FlowExecution Management Update Component', () => {
    let comp: FlowExecutionUpdateComponent;
    let fixture: ComponentFixture<FlowExecutionUpdateComponent>;
    let service: FlowExecutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [FlowExecutionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FlowExecutionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FlowExecutionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FlowExecutionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FlowExecution(123);
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
        const entity = new FlowExecution();
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
