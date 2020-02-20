import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { EndpointPropertyUpdateComponent } from 'app/entities/endpoint-property/endpoint-property-update.component';
import { EndpointPropertyService } from 'app/entities/endpoint-property/endpoint-property.service';
import { EndpointProperty } from 'app/shared/model/endpoint-property.model';

describe('Component Tests', () => {
  describe('EndpointProperty Management Update Component', () => {
    let comp: EndpointPropertyUpdateComponent;
    let fixture: ComponentFixture<EndpointPropertyUpdateComponent>;
    let service: EndpointPropertyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [EndpointPropertyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EndpointPropertyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EndpointPropertyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EndpointPropertyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EndpointProperty(123);
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
        const entity = new EndpointProperty();
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
