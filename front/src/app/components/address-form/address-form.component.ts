import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-address-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './address-form.component.html'
})
export class AddressFormComponent {
  @Input() enderecoForm!: FormGroup;
  @Input() index!: number;
  @Output() buscarCep = new EventEmitter<number>();
  @Output() remover = new EventEmitter<number>();

  onBuscarCep() {
    this.buscarCep.emit(this.index);
  }

  onRemover() {
    this.remover.emit(this.index);
  }
}
