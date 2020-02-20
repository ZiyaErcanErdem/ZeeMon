import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { CheckScriptUpdateComponent } from 'app/entities/check-script/check-script-update.component';
import { CheckScriptService } from 'app/entities/check-script/check-script.service';
import { CheckScript } from 'app/shared/model/check-script.model';

describe('Component Tests', () => {
  describe('CheckScript Management Update Component', () => {
    let comp: CheckScriptUpdateComponent;
    let fixture: ComponentFixture<CheckScriptUpdateComponent>;
    let service: CheckScriptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [CheckScriptUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CheckScriptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CheckScriptUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckScriptService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CheckScript(123);
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
        const entity = new CheckScript();
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
