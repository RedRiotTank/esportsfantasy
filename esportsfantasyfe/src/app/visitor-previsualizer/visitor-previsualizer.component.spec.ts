import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitorPrevisualizerComponent } from './visitor-previsualizer.component';

describe('VisitorPrevisualizerComponent', () => {
  let component: VisitorPrevisualizerComponent;
  let fixture: ComponentFixture<VisitorPrevisualizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VisitorPrevisualizerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VisitorPrevisualizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
