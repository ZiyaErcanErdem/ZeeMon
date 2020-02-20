import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ContentValidationErrorUpdateComponent } from 'app/entities/content-validation-error/content-validation-error-update.component';
import { ContentValidationErrorService } from 'app/entities/content-validation-error/content-validation-error.service';
import { ContentValidationError } from 'app/shared/model/content-validation-error.model';

describe('Component Tests', () => {
  describe('ContentValidationError Management Update Component', () => {
    let comp: ContentValidationErrorUpdateComponent;
    let fixture: ComponentFixture<ContentValidationErrorUpdateComponent>;
    let service: ContentValidationErrorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ContentValidationErrorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContentValidationErrorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentValidationErrorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentValidationErrorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContentValidationError(123);
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
        const entity = new ContentValidationError();
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
