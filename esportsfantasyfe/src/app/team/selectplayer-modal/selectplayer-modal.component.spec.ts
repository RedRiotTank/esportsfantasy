import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectplayerModalComponent } from './selectplayer-modal.component';

describe('SelectplayerModalComponent', () => {
  let component: SelectplayerModalComponent;
  let fixture: ComponentFixture<SelectplayerModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SelectplayerModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SelectplayerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
