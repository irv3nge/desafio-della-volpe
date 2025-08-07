import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { ViaCepService } from '../../services/viacep.service';
import { ToastComponent } from '../../shared/toast/toast.component';
import { SpinnerComponent } from '../../shared/spinner/spinner.component';
import { AddressFormComponent } from '../address-form/address-form.component';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    ToastComponent,
    SpinnerComponent,
    AddressFormComponent
  ],
  templateUrl: './user-form.component.html',
})
export class UserFormComponent implements OnInit {
  form: FormGroup;
  loading = false;
  showToast = false;
  toastMessage = '';
  toastType: 'success' | 'error' = 'success';
  isEdit = false;
  userId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private viaCepService: ViaCepService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', Validators.required],
      enderecos: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.userId = +id;
      this.carregarUsuario(this.userId);
    } else {
      this.adicionarEndereco();
    }
  }

  get enderecos(): FormArray {
    return this.form.get('enderecos') as FormArray;
  }
  get enderecosFormGroups(): FormGroup[] {
  return this.enderecos.controls as FormGroup[];
}


  novoEndereco(): FormGroup {
    return this.fb.group({
      cep: ['', Validators.required],
      rua: [''],
      numero: ['', Validators.required],
      estado: [''],
      cidade: [''],
      bairro: ['']
    });
  }

  adicionarEndereco() {
    this.enderecos.push(this.novoEndereco());
  }

  removerEndereco(index: number) {
    this.enderecos.removeAt(index);
  }

  buscarCEP(index: number) {
  const cep = this.enderecos.at(index).get('cep')?.value;
  if (!cep || cep.length !== 8) {
    this.mostrarToast('CEP inválido.', 'error');
    return;
  }

  this.loading = true;
  this.viaCepService.buscar(cep).subscribe({
    next: (res: any) => {
      this.loading = false;

      if (res?.erro) {
        this.mostrarToast('CEP não encontrado.', 'error');
        return;
      }

      this.enderecos.at(index).patchValue({
        rua: res.logradouro,
        estado: res.uf,
        cidade: res.localidade,
        bairro: res.bairro,
      });
    },
    error: (err) => {
      this.loading = false;
      console.error('Erro ao consultar CEP', err);
      this.mostrarToast('Erro ao consultar o CEP.', 'error');
    }
  });
}

  salvar() {
    if (this.form.invalid) return;

    const user = this.form.value;
    this.loading = true;

    const request = this.isEdit
      ? this.userService.atualizar({ id: this.userId, ...user })
      : this.userService.salvar(user);

    request.subscribe({
      next: () => {
        this.mostrarToast('Usuário salvo com sucesso!', 'success');
        setTimeout(() => this.router.navigate(['/']), 1500);
        this.loading = false;
      },
      error: () => {
        this.mostrarToast('Erro ao salvar usuário.', 'error');
        this.loading = false;
      }
    });
  }

  carregarUsuario(id: number) {
    this.loading = true;
    this.userService.buscarPorId(id).subscribe({
      next: (res) => {
        this.form.patchValue({
          nome: res.nome,
          email: res.email,
          telefone: res.telefone,
        });
        this.enderecos.clear();
        res.enderecos.forEach((e: any) => {
          this.enderecos.push(this.fb.group(e));
        });
        this.loading = false;
      },
      error: () => {
        this.mostrarToast('Erro ao carregar usuário.', 'error');
        this.loading = false;
      }
    });
  }

  mostrarToast(msg: string, tipo: 'success' | 'error') {
    this.toastMessage = msg;
    this.toastType = tipo;
    this.showToast = true;
    setTimeout(() => (this.showToast = false), 3000);
  }
}
