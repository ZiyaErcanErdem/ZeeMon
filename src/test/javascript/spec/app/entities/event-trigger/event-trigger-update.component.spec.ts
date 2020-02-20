import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { EventTriggerUpdateComponent } from 'app/entities/event-trigger/event-trigger-update.component';
import { EventTriggerService } from 'app/entities/event-trigger/event-trigger.service';
import { EventTrigger } from 'app/shared/model/event-trigger.model';

describe('Component Tests', () => {
  describe('EventTrigger Management Update Component', () => {
    let comp: EventTriggerUpdateComponent;
    let fixture: ComponentFixture<EventTriggerUpdateComponent>;
    let service: EventTriggerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [EventTriggerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventTriggerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventTriggerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventTriggerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventTrigger(123);
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
        const entity = new EventTrigger();
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
