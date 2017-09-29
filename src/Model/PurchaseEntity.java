/*
 * ICBT Student ID : CL/MSCIT/11/03
 * University ID : 20127172
 * Author : Susiri Indika Gunasekara
 */

package Model;
import java.util.Date;

public class PurchaseEntity {
 
	public int PurchaseId;
	 
	public Date PurchaseDate;
	 
	public boolean IsActive;
	 
	public SupplierEntity Supplier;
	 
	public ItemEntity[] Items;
	 
	public UserEntity User;
	 
	public void Save() {
	 
	}
	 
}
 
