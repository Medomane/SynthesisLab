import {Component, OnInit, ViewChild} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from '../app.component';
import {CustomerService} from './customer.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  rowData:any[] = [];
  currentCustomer: any;
  // @ts-ignore
  public customerForm: FormGroup;
  // @ts-ignore
  public message: string;
  //@ViewChild('content') content: any;
  submitted = false;
  isEditing = false;
  constructor(public service:CustomerService,private modalService: NgbModal,public app:AppComponent, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.service.getCustomers().subscribe(v =>{
      // @ts-ignore
      this.rowData = v._embedded.customers;
      this.app.subTitle = 'Customers';
      this.setValidators();
    },(er)=>{
      console.error(er);
    });
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
    this.currentCustomer = customer;
    this.submitted = false;
    this.message = '';
    this.isEditing = true;
    this.setValidators();
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
        this.service.deleteCustomer(customer.id).subscribe(v1 => {
          Swal.fire(
            'Success!',
            'Deleted successfully!',
            'success'
          );
          this.rowData.splice(this.rowData.indexOf(customer),1);
        }, error => { console.error(error) ; this.message = error.message; });;
      }
    });
  }

  open(content:any) {
    this.submitted = false;
    this.isEditing = false;
    this.message = '';
    this.setValidators();
    this.currentCustomer = {};
    this.customerForm.controls.id.setValue(null);
    this.modalService.open(content);
  }

  onSubmit() {
    this.message = '';
    this.submitted = true;
    if (this.customerForm.invalid) { return; }
    this.service.saveCustomer(this.customerForm.value).subscribe(v1 => {
      if(!this.isEditing) {
        this.rowData.push(v1);
        this.message = "Added successfully.";
      }
      else{
        this.rowData.forEach(function(e,i){
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
    this.customerForm.reset();
  }

}
