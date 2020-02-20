import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { FieldMappingUpdateComponent } from 'app/entities/field-mapping/field-mapping-update.component';
import { FieldMappingService } from 'app/entities/field-mapping/field-mapping.service';
import { FieldMapping } from 'app/shared/model/field-mapping.model';

describe('Component Tests', () => {
  describe('FieldMapping Management Update Component', () => {
    let comp: FieldMappingUpdateComponent;
    let fixture: ComponentFixture<FieldMappingUpdateComponent>;
    let service: FieldMappingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [FieldMappingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FieldMappingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FieldMappingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FieldMappingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FieldMapping(123);
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
        const entity = new FieldMapping();
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
