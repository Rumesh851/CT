/*
 * ICBT Student ID : CL/MSCIT/11/03
 * University ID : 20127172
 * Author : Susiri Indika Gunasekara
 */
package Controler;

import Model.CustomerEntity;

public class CustomerController {

    public Model.CustomerEntity Customer;

    public CustomerController() {
        Customer = new CustomerEntity();
    }

    public void Save() {
        Customer.Save();
    }

    public Model.CustomerEntity Get(int Id) {

        Customer.Get(Id);

        return Customer;
    }

}
