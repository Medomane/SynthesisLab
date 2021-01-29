import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from '../app.component';
import Swal from 'sweetalert2';
import {SupplierService} from './supplier.service';

@Component({
  selector: 'app-suppliers',
  templateUrl: './suppliers.component.html',
  styleUrls: ['./suppliers.component.css']
})
export class SuppliersComponent implements OnInit {

  rowData:any[] = [];
  // @ts-ignore
  public supplierForm: FormGroup;
  public message: string;
  submitted:boolean = false;
  isEditing:boolean = false;
  constructor(public service:SupplierService,private modalService: NgbModal,public app:AppComponent, private formBuilder: FormBuilder) {
    this.message = '';
  }

  ngOnInit(): void {
    this.service.getSuppliers().subscribe(v =>{
      // @ts-ignore
      this.rowData = v._embedded.suppliers;
      this.app.subTitle = 'Suppliers';
      this.setValidators();
    },(er)=>{
      console.error(er);
    });
  }

  open(content:any) {
    this.submitted = false;
    this.isEditing = false;
    this.message = '';
    this.supplierForm.controls.id.setValue(null);
    this.modalService.open(content);
  }
  private setValidators() {
    this.supplierForm = this.formBuilder.group({
      id: [''],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() { return this.supplierForm.controls; }

  edit(content: any,supplier: any) {
    this.submitted = false;
    this.message = '';
    this.isEditing = true;
    this.supplierForm.controls["id"].setValue(supplier.id);
    this.supplierForm.controls["name"].setValue(supplier.name);
    this.supplierForm.controls["email"].setValue(supplier.email);
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }
  delete(supplier: any){
    Swal.fire({
      title: 'Confirmation' ,
      text: 'Do you really wanna delete '+supplier.name+'?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes!'
    }).then((result) => {
      if (result.value) {
        this.service.deleteSupplier(supplier.id).subscribe(() => {
          Swal.fire(
            'Success!',
            'Deleted successfully!',
            'success'
          ).then(()=>{
            this.rowData.splice(this.rowData.indexOf(supplier),1);
          });
        }, error => { console.error(error) ; this.message = error.message; });
      }
    });
  }


  onSubmit() {
    this.message = '';
    this.submitted = true;
    if (this.supplierForm.invalid) { return; }
    this.service.saveSupplier(this.supplierForm.value).subscribe(v1 => {
      if(!this.isEditing) {
        this.rowData.push(v1);
        this.message = "Added successfully.";
      }
      else{
        this.rowData.forEach(function(e){
          // @ts-ignore
          if(e.id === v1.id){
            // @ts-ignore
            e.name = v1.name;
            // @ts-ignore
            e.email = v1.email;
          }
        })
      }
      this.modalService.dismissAll();
    }, error => { console.error(error) ; this.message = error.message; });
  }

  onReset() {
    this.submitted = false;
    this.supplierForm.reset();
  }

}
