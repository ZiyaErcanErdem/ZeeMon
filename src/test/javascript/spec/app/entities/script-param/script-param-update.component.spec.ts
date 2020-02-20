import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ScriptParamUpdateComponent } from 'app/entities/script-param/script-param-update.component';
import { ScriptParamService } from 'app/entities/script-param/script-param.service';
import { ScriptParam } from 'app/shared/model/script-param.model';

describe('Component Tests', () => {
  describe('ScriptParam Management Update Component', () => {
    let comp: ScriptParamUpdateComponent;
    let fixture: ComponentFixture<ScriptParamUpdateComponent>;
    let service: ScriptParamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ScriptParamUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ScriptParamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScriptParamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScriptParamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScriptParam(123);
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
        const entity = new ScriptParam();
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
