import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionUpdateComponent } from 'app/entities/action/action-update.component';
import { ActionService } from 'app/entities/action/action.service';
import { Action } from 'app/shared/model/action.model';

describe('Component Tests', () => {
  describe('Action Management Update Component', () => {
    let comp: ActionUpdateComponent;
    let fixture: ComponentFixture<ActionUpdateComponent>;
    let service: ActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Action(123);
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
        const entity = new Action();
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
