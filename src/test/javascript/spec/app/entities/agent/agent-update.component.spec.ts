import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ZeemonTestModule } from '../../../test.module';
import { AgentUpdateComponent } from 'app/entities/agent/agent-update.component';
import { AgentService } from 'app/entities/agent/agent.service';
import { Agent } from 'app/shared/model/agent.model';

describe('Component Tests', () => {
  describe('Agent Management Update Component', () => {
    let comp: AgentUpdateComponent;
    let fixture: ComponentFixture<AgentUpdateComponent>;
    let service: AgentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZeemonTestModule],
        declarations: [AgentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AgentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agent(123);
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
        const entity = new Agent();
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
