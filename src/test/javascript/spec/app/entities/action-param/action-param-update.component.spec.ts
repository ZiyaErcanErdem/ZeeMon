import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionParamUpdateComponent } from 'app/entities/action-param/action-param-update.component';
import { ActionParamService } from 'app/entities/action-param/action-param.service';
import { ActionParam } from 'app/shared/model/action-param.model';

describe('Component Tests', () => {
  describe('ActionParam Management Update Component', () => {
    let comp: ActionParamUpdateComponent;
    let fixture: ComponentFixture<ActionParamUpdateComponent>;
    let service: ActionParamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionParamUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActionParamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionParamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActionParamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActionParam(123);
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
        const entity = new ActionParam();
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
