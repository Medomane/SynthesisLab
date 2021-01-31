import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from '../app.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  rowData:any[] = [];
  // @ts-ignore
  public customerForm: FormGroup;
  public message: string='';
  submitted:boolean = false;
  isEditing:boolean = false;

  constructor(private modalService: NgbModal,public app:AppComponent, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.app.http.get(this.app.customersUrl).subscribe(v =>{
      // @ts-ignore
      this.rowData = v;
      this.app.subTitle = 'Customers';
      this.setValidators();
    },(er)=>{
      console.error(er);
    });
  }

  open(content:any) {
    this.isEditing = false;
    this.onReset();
    this.modalService.open(content);
  }

  private setValidators() {
    this.customerForm = this.formBuilder.group({
      id: [''],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() { return this.customerForm.controls; }

  edit(content: any,customer: any) {
    this.submitted = false;
    this.message = '';
    this.isEditing = true;
    this.customerForm.controls["id"].setValue(customer.id);
    this.customerForm.controls["name"].setValue(customer.name);
    this.customerForm.controls["email"].setValue(customer.email);
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }

  delete(customer: any){
    Swal.fire({
      title: 'Confirmation' ,
      text: 'Do you really wanna delete '+customer.name+'?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes!'
    }).then((result) => {
      if (result.value) {
        this.app.http.delete(this.app.customersUrl+customer.id,{headers:this.app.keyCloak.getHeader()}).subscribe(() => {
          Swal.fire(
            'Success!',
            'Deleted successfully!',
            'success'
          ).then(()=>{
            this.rowData.splice(this.rowData.indexOf(customer),1);
          });
        }, error => { console.error(error) ; this.message = error.message; });
      }
    });
  }

  onSubmit() {
    this.message = '';
    this.submitted = true;
    if (this.customerForm.invalid) { return; }
    this.app.http.post(this.app.customersUrl,this.customerForm.value,{headers:this.app.keyCloak.getHeader()}).subscribe(v1 => {
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
    this.message = '';
    this.customerForm.reset();
  }
}
