import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ActionScriptUpdateComponent } from 'app/entities/action-script/action-script-update.component';
import { ActionScriptService } from 'app/entities/action-script/action-script.service';
import { ActionScript } from 'app/shared/model/action-script.model';

describe('Component Tests', () => {
  describe('ActionScript Management Update Component', () => {
    let comp: ActionScriptUpdateComponent;
    let fixture: ComponentFixture<ActionScriptUpdateComponent>;
    let service: ActionScriptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ActionScriptUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActionScriptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionScriptUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActionScriptService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActionScript(123);
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
        const entity = new ActionScript();
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
