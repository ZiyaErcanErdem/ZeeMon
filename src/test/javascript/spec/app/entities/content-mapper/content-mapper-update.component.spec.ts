import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { ContentMapperUpdateComponent } from 'app/entities/content-mapper/content-mapper-update.component';
import { ContentMapperService } from 'app/entities/content-mapper/content-mapper.service';
import { ContentMapper } from 'app/shared/model/content-mapper.model';

describe('Component Tests', () => {
  describe('ContentMapper Management Update Component', () => {
    let comp: ContentMapperUpdateComponent;
    let fixture: ComponentFixture<ContentMapperUpdateComponent>;
    let service: ContentMapperService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [ContentMapperUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContentMapperUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentMapperUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentMapperService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContentMapper(123);
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
        const entity = new ContentMapper();
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
