import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { SpinnerComponent } from '../../shared/spinner/spinner.component';
import { ToastComponent } from '../../shared/toast/toast.component';

@Component({
  selector: 'app-user-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, SpinnerComponent, ToastComponent],
  templateUrl: './user-detail.component.html',
})
export class UserDetailComponent implements OnInit {
  user: any;
  loading = false;
  showToast = false;
  toastMessage = '';
  toastType: 'success' | 'error' = 'success';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.carregarUsuario(+id);
    }
  }

  carregarUsuario(id: number) {
    this.loading = true;
    this.userService.buscarPorId(id).subscribe({
      next: (res) => {
        this.user = res;
        this.loading = false;
      },
      error: () => {
        this.mostrarToast('Erro ao carregar usuário', 'error');
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
