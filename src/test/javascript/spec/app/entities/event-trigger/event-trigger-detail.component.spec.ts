import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { EventTriggerDetailComponent } from 'app/entities/event-trigger/event-trigger-detail.component';
import { EventTrigger } from 'app/shared/model/event-trigger.model';

describe('Component Tests', () => {
  describe('EventTrigger Management Detail Component', () => {
    let comp: EventTriggerDetailComponent;
    let fixture: ComponentFixture<EventTriggerDetailComponent>;
    const route = ({ data: of({ eventTrigger: new EventTrigger(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [EventTriggerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventTriggerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventTriggerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eventTrigger on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventTrigger).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
