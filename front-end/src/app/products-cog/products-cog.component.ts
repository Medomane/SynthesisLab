import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from '../app.component';
import {ProductService} from '../products/product.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-products-cog',
  templateUrl: './products-cog.component.html',
  styleUrls: ['./products-cog.component.css']
})
export class ProductsCogComponent implements OnInit {
  rowData:any[] = [];
  suppliers:any[] = [];
  // @ts-ignore
  public productForm: FormGroup;
  public currentImage:string='';
  public currentImageFile:any;
  public message: string='';
  submitted:boolean = false;
  isEditing:boolean = false;
  constructor(public service:ProductService,private modalService: NgbModal,public app:AppComponent, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.app.http.get(this.app.productsUrl).subscribe(v =>{
      // @ts-ignore
      this.rowData = v;
      this.app.subTitle = 'Products';
      this.setValidators();
      this.app.http.get(this.app.suppliersUrl).subscribe(supp=>{
        // @ts-ignore
        this.suppliers = supp;
      });
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
    this.productForm = this.formBuilder.group({
      id: [''],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      quantityAvailable: ['', [Validators.required, Validators.min(1)]],
      supplierId: ['', Validators.required]
    });
  }
  get f() { return this.productForm.controls; }
  edit(content: any,product: any) {
    this.onReset();
    this.isEditing = true;
    this.productForm.controls["id"].setValue(product.id);
    this.productForm.controls["name"].setValue(product.name);
    this.productForm.controls["price"].setValue(product.price);
    this.productForm.controls["quantityAvailable"].setValue(product.quantityAvailable);
    this.productForm.controls["supplierId"].setValue(product.supplier.id);
    this.currentImage = this.app.productsUrl+product.id+"/icon";
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }
  delete(product: any){
    Swal.fire({
      title: 'Confirmation' ,
      text: 'Do you really wanna delete '+product.name+'?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes!'
    }).then((result) => {
      if (result.value) {
        this.service.deleteProduct(product.id).subscribe(() => {
          Swal.fire(
            'Success!',
            'Deleted successfully!',
            'success'
          ).then(()=>{
            this.rowData.splice(this.rowData.indexOf(product),1);
          });
        }, error => { console.error(error) ; this.message = error.message; });
      }
    });
  }

  onSubmit() {
    this.message = '';
    this.submitted = true;
    if(this.currentImage === '') {
      this.message = "Image required !!!";
      return ;
    }
    if (this.productForm.invalid) { return; }
    this.service.saveProduct(this.productForm.value,this.currentImageFile,this.app.productsUrl,this.app.keyCloak.getHeader()).subscribe(v1 => {
      if(!this.isEditing) {
        console.log(v1);
        this.rowData.push(v1);
        this.message = "Added successfully.";
      }
      else{
        this.rowData.forEach(function(e){
          // @ts-ignore
          if(e.id === v1.id){
            console.log(e,v1);
            // @ts-ignore
            e.name = v1.name;
            // @ts-ignore
            e.price = v1.price;
            // @ts-ignore
            e.quantityAvailable = v1.quantityAvailable;
            // @ts-ignore
            e.supplier = v1.supplier;
          }
        })
      }
      this.modalService.dismissAll();
    }, error => { console.error(error) ; this.message = error.message; });
  }

  onReset() {
    this.submitted = false;
    this.currentImage = '';
    this.currentImageFile = undefined;
    this.message = '';
    this.productForm.reset();
  }
  onFileChange(event:any) {
    const reader = new FileReader();
    if(event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.currentImage = reader.result as string;
        this.message = '';
        this.currentImageFile = file;
      };
    }
  }
}
