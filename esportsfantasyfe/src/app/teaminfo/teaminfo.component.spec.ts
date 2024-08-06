import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeaminfoComponent } from './teaminfo.component';

describe('TeaminfoComponent', () => {
  let component: TeaminfoComponent;
  let fixture: ComponentFixture<TeaminfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TeaminfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeaminfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
