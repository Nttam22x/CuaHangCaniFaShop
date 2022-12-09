package com.example.canifa_shop.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.canifa_shop.Bill.Object.Bill;
import com.example.canifa_shop.Category.Object.Category;
import com.example.canifa_shop.Customer.Object.Customer;
import com.example.canifa_shop.Login.Object.Accounts;
import com.example.canifa_shop.Manager.Object.Receipt;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.Report.Objcet.Report;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;
    static final String DB_NAME = "Data";
    static final String DB_TABLE_ACCOUNT = "Accounts";
    static final String DB_TABLE_PRODUCT = "Products";
    static final String DB_TABLE_ORDER_PRODUCT = "OrderProducts";
    static final String DB_TABLE_REPORT = "Reports";
    static final String DB_TABLE_BILL = "Bills";
    static final String DB_TABLE_CATEGORY = "Categorys";
    static final String DB_TABLE_CUSTOMER = "Customers";
    static final String DB_TABLE_RECEIPT = "Receipts";
    static final int DB_VERSION = 1;
    // các trường của bảng account
    static final String ACCOUNT_ID = "ID";
    static final String ACCOUNT_USER_NAME = "userName";
    static final String ACCOUNT_PASSWORD = "password";
    static final String ACCOUNT_FULL_NAME = "fullName";
    static final String ACCOUNT_DATE_OF_BIRTH = "dateOfBirth";
    static final String ACCOUNT_PHONE = "phone";
    static final String ACCOUNT_EMAIL = "email";
    static final String ACCOUNT_AVATAR = "avatar";
    static final String ACCOUNT_HOMETOWN = "homeTown";
    static final String ACCOUNT_PERMISSION = "permission";
    // các trường của bảng product
    static final String PRODUCT_ID = "ID";
    static final String PRODUCT_NAME = "name";
    static final String PRODUCT_PRICE_IMPORT = "priceImport";
    static final String PRODUCT_PRICE = "price";
    static final String PRODUCT_AMOUNT = "amount";
    static final String PRODUCT_TYPE = "type";
    static final String PRODUCT_DESCRIBE = "describe";
    static final String PRODUCT_IMAGE = "image";
    static final String PRODUCT_BARD_CODE = "producer";
    // các trường của bảng report
    static final String REPORT_ID = "IDReport";
    static final String REPORT_DATE = "date";
    static final String REPORT_TOTAL_IMPORT = "totalImport";
    static final String REPORT_TOTAL_SALE = "totalSale";
    static final String REPORT_SALE_MONEY = "saleMoney";
    //các trường của bảng phiếu nhập
    static final String RECEIPT_ID = "IDReceipt";
    static final String RECEIPT_DATE = "dateCreate";
    static final String RECEIPT_ID_PRODUCT = "IDProduct";
    static final String RECEIPT_TOTAL_PRODUCT = "totalProduct";
    static final String RECEIPT_ID_EMPLOYEE = "IDEmployee";
    // các trường của bảng danh mục
    static final String CATEGORY_ID = "ID";
    static final String CATEGORY_NAME = "nameCategory";
    static final String CATEGORY_TOTAL_PRODUCT = "totalProduct";
    static final String CATEGORY_DESCRIBE = "describe";
    // các trường của bảng bill
    static final String BILL_ID = "IDBill";
    static final String BILL_DATE = "date";
    static final String BILL_NAME_PRODUCT = "names";
    static final String BILL_AMOUNT = "amounts";
    static final String BILL_PRICE = "price";
    static final String BILL_TOTAL = "total";
    static final String BILL_ID_CUSTOMER = "IDCustomer";
    static final String BILL_ID_EMPLOYEE = "IDEmployee";
    //các trường cảu bảng customer

    static final String CUSTOMER_ID = "ID";
    static final String CUSTOMER_NAME = "customerName";
    static final String CUSTOMER_PHONE = "customerPhone";
    static final String CUSTOMER_EMAIL = "customerEmail";
    static final String CUSTOMER_ADDRESS = "customerAddress";
    static final String CUSTOMER_ACCUMULATE_POINTS = "customerPoints";
    static final String CUSTOMER_TYPE = "customerType";
    static final String CUSTOMER_VOUCHER = "customerVoucher";

    // tất cả các bản sẽ có 4 hàm để sử lý
    /*
        hàm insert
        hàm update
        hàm delete
        hàm getAll
     */

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTableAccounts = "CREATE TABLE " + DB_TABLE_ACCOUNT + "(" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "userName TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "fullName TEXT NOT NULL," +
                "dateOfBirth TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "avatar TEXT," +
                "homeTown TEXT NOT NULL," +
                "permission TEXT )";
        db.execSQL(queryCreateTableAccounts);
        String queryCreateTableCustomers = "CREATE TABLE " + DB_TABLE_CUSTOMER + "(" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "customerName TEXT NOT NULL," +
                "customerPhone TEXT NOT NULL," +
                "customerEmail TEXT NOT NULL," +
                "customerAddress TEXT NOT NULL," +
                "customerPoints TEXT NOT NULL," +
                "customerType TEXT NOT NULL," +
                "customerVoucher TEXT NOT NULL)";
        String queryCreateTableProducts = "CREATE TABLE Products (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "priceImport LONG NOT NULL," +
                "price LONG NOT NULL," +
                "amount INTEGER NOT NULL," +
                "type TEXT NOT NULL," +
                "describe TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "producer TEXT NOT NULL)";
        String queryCreateTableOrderProducts = "CREATE TABLE OrderProducts (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "priceImport LONG NOT NULL," +
                "price LONG NOT NULL," +
                "amount INTEGER NOT NULL," +
                "type TEXT NOT NULL," +
                "describe TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "producer TEXT NOT NULL)";
        String queryCreateTableReports = "CREATE TABLE Reports (" +
                "IDReport INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "totalImport LONG NOT NULL," +
                "totalSale LONG NOT NULL," +
                "saleMoney LONG)";
        String queryCreateTableReceipt = "CREATE TABLE  Receipts (" +
                "IDReceipt INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "dateCreate TEXT NOT NULL," +
                "IDProduct TEXT NOT NULL," +
                "totalProduct INTEGER NOT NULL," +
                "IDEmployee LONG NOT NULL)";

        String queryCreateTableCategorys = "CREATE TABLE " + DB_TABLE_CATEGORY + " (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nameCategory TEXT NOT NULL," +
                "totalProduct LONG NOT NULL," +
                "describe TEXT)";
        String queryCreateTableBillProducts = "CREATE TABLE Bills (" +
                "IDBill INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "names TEXT NOT NULL," +
                "amounts TEXT NOT NULL," +
                "price TEXT NOT NULL," +
                "total LONG NOT NULL," +
                "IDCustomer INTERGER NOT NULL," +
                "IDEmployee INTERGER NOT NULL)";
        db.execSQL(queryCreateTableCategorys);
        db.execSQL(queryCreateTableReceipt);
        db.execSQL(queryCreateTableProducts);
        db.execSQL(queryCreateTableOrderProducts);
        db.execSQL(queryCreateTableReports);
        db.execSQL(queryCreateTableBillProducts);
        db.execSQL(queryCreateTableCustomers);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }

    public void insertAccount(Accounts account) {  // thêm tài khoản mới
        sqLiteDatabase = getWritableDatabase(); // gọi phương thức getWriteableDatabase cho phép sửa dữ liệu
        contentValues = new ContentValues();
        // thực hiện truyền dữ liệu vào biến contentValues. Vì ID để là AUTOINCREMENT nên không truyền vào ID để insert
        contentValues.put(ACCOUNT_USER_NAME, account.getUserName());
        contentValues.put(ACCOUNT_PASSWORD, account.getPassword());
        contentValues.put(ACCOUNT_FULL_NAME, account.getFullName());
        contentValues.put(ACCOUNT_DATE_OF_BIRTH, account.getDateOfBirth());
        contentValues.put(ACCOUNT_PHONE, account.getPhone());
        contentValues.put(ACCOUNT_EMAIL, account.getEmail());
        contentValues.put(ACCOUNT_AVATAR, account.getAvatar());
        contentValues.put(ACCOUNT_HOMETOWN, account.getHomeTown());
        contentValues.put(ACCOUNT_PERMISSION, account.getPermission());
        //sử dụng câu lệnh insert để thực hiện insert vào bảng Accounts
        sqLiteDatabase.insert(DB_TABLE_ACCOUNT, null, contentValues);
        contentValues.clear();
    }

    public void updateAccount(Accounts account) {  // update thông tin cá nhân
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(ACCOUNT_USER_NAME, account.getUserName());
        contentValues.put(ACCOUNT_PASSWORD, account.getPassword());
        contentValues.put(ACCOUNT_FULL_NAME, account.getFullName());
        contentValues.put(ACCOUNT_DATE_OF_BIRTH, account.getDateOfBirth());
        contentValues.put(ACCOUNT_PHONE, account.getPhone());
        contentValues.put(ACCOUNT_HOMETOWN, account.getHomeTown());
        contentValues.put(ACCOUNT_EMAIL, account.getEmail());
        contentValues.put(ACCOUNT_AVATAR, account.getAvatar());
        contentValues.put(ACCOUNT_PERMISSION, account.getPermission());
        sqLiteDatabase.update(DB_TABLE_ACCOUNT, contentValues, "ID = ?", new String[]{String.valueOf(account.getAccountID())});
        // update = ở bảng nào , dữ liệu truyền vào, update theo cái gì ( ID ), ID truyền vào
        contentValues.clear();
    }

    public void deleteAccount(int id) {   // xóa accounts với ID được truyền vào
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_ACCOUNT, "ID = ?", new String[]{String.valueOf(id)});
        //xóa = ở bảng nào, xóa theo gì (id), id truyền vào
    }

    public List<Accounts> getAllAccounts() {   // hàm trả về danh sách tài khoản
        List<Accounts> accountList = new ArrayList<>();
        Accounts account;
        sqLiteDatabase = getReadableDatabase();
        try {
            // cusor là để lưu dữ liệu tạm thời
            cursor = sqLiteDatabase.query(false, DB_TABLE_ACCOUNT, null, null, null, null, null,
                    null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                // câu lệnh cursor.get<type_data> để lấy dữ liệu từ bảng
                int ID = cursor.getInt(cursor.getColumnIndex(ACCOUNT_ID));
                String userName = cursor.getString(cursor.getColumnIndex(ACCOUNT_USER_NAME));
                String password = cursor.getString(cursor.getColumnIndex(ACCOUNT_PASSWORD));
                String fullName = cursor.getString(cursor.getColumnIndex(ACCOUNT_FULL_NAME));
                String dateOfBirth = cursor.getString(cursor.getColumnIndex(ACCOUNT_DATE_OF_BIRTH));
                String phone = cursor.getString(cursor.getColumnIndex(ACCOUNT_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(ACCOUNT_EMAIL));
                String homeTown = cursor.getString(cursor.getColumnIndex(ACCOUNT_HOMETOWN));
                String permission = cursor.getString(cursor.getColumnIndex(ACCOUNT_PERMISSION));
                String avatar = cursor.getString(cursor.getColumnIndex(ACCOUNT_AVATAR));
                account = new Accounts(ID, userName, password, fullName, dateOfBirth, phone, email, homeTown, avatar, permission);
                accountList.add(account);
            }
        return accountList;
    }

    public void insertProduct(Product product) {//thêm product
        sqLiteDatabase = getWritableDatabase();// gọi phương thức getWriteableDatabase cho phép sửa dữ liệu
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(PRODUCT_NAME, product.getNameProduct());
        contentValues.put(PRODUCT_PRICE_IMPORT, product.getImportprice());
        contentValues.put(PRODUCT_PRICE, product.getPrice());
        contentValues.put(PRODUCT_AMOUNT, product.getAmount());
        contentValues.put(PRODUCT_TYPE, product.getType());
        contentValues.put(PRODUCT_DESCRIBE, product.getDescribe());
        contentValues.put(PRODUCT_IMAGE, product.getImage());
        contentValues.put(PRODUCT_BARD_CODE, product.getBardCode());
        //sử dụng câu lệnh insert để thực hiện insert vào bảng Products
        sqLiteDatabase.insert(DB_TABLE_PRODUCT, null, contentValues);
        sqLiteDatabase.close();
    }

    public void updateProduct(Product product) {//sửa sản phẩm
        sqLiteDatabase = getWritableDatabase();// gọi phương thức getWriteableDatabase cho phép sửa dữ liệu
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(PRODUCT_NAME, product.getNameProduct());
        contentValues.put(PRODUCT_PRICE_IMPORT, product.getImportprice());
        contentValues.put(PRODUCT_PRICE, product.getPrice());
        contentValues.put(PRODUCT_AMOUNT, product.getAmount());
        contentValues.put(PRODUCT_TYPE, product.getType());
        contentValues.put(PRODUCT_DESCRIBE, product.getDescribe());
        contentValues.put(PRODUCT_IMAGE, product.getImage());
        contentValues.put(PRODUCT_BARD_CODE, product.getBardCode());
        //sử dụng câu lệnh update để thực hiện insert vào bảng Products
        sqLiteDatabase.update(DB_TABLE_PRODUCT, contentValues, PRODUCT_BARD_CODE + " = ?", new String[]{product.getBardCode()});
        // update = ở bảng nào , dữ liệu truyền vào, update theo cái gì ( ID ), ID truyền vào
        sqLiteDatabase.close();
    }


    public void deleteAllProduct() {//xóa sản phẩm
        sqLiteDatabase = getWritableDatabase();// gọi phương thức getWriteableDatabase cho phép sửa dữ liệu
        //sử dụng câu lệnh delete để thực hiện insert vào bảng Products
        sqLiteDatabase.delete(DB_TABLE_PRODUCT, null, null);

    }

    public void deleteItemProduct(int ID) {
        sqLiteDatabase = getWritableDatabase();// gọi phương thức getWriteableDatabase cho phép sửa dữ liệu
        sqLiteDatabase.delete(DB_TABLE_PRODUCT, "ID = ?", new String[]{String.valueOf(ID)});
        //xóa = ở bảng nào, xóa theo gì (id), id truyền vào
    }

    public List<Product> getAllPrduct() {
        List<Product> productList = new ArrayList<>();
        Product product;
        sqLiteDatabase = getReadableDatabase();// gọi phương thức getReadableDatabase đọc dữ liệu trong database
        try {
            //cursor dùng để đóng gói giá trị tạm thời
            cursor = sqLiteDatabase.query(false, DB_TABLE_PRODUCT, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                // câu lệnh cursor.get...<type_data> để lấy dữ liệu từ bảng
                int ID = cursor.getInt(cursor.getColumnIndex(ACCOUNT_ID));
                String nameProduct = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
                long priceImport = cursor.getLong(cursor.getColumnIndex(PRODUCT_PRICE_IMPORT));
                long price = cursor.getLong(cursor.getColumnIndex(PRODUCT_PRICE));
                int amount = cursor.getInt(cursor.getColumnIndex(PRODUCT_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndex(PRODUCT_TYPE));
                String describe = cursor.getString(cursor.getColumnIndex(PRODUCT_DESCRIBE));
                String image = cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGE));
                String bardCode = cursor.getString(cursor.getColumnIndex(PRODUCT_BARD_CODE
                ));
                //gọi tất cả các phần tử vào listProduct
                productList.add(new Product(ID, nameProduct, priceImport, price, amount, type, describe, image, bardCode));
            }
        return productList;
    }

    public void insertOrderProduct(Product product) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(PRODUCT_NAME, product.getNameProduct());
        contentValues.put(PRODUCT_PRICE_IMPORT, product.getImportprice());
        contentValues.put(PRODUCT_PRICE, product.getPrice());
        contentValues.put(PRODUCT_AMOUNT, product.getAmount());
        contentValues.put(PRODUCT_TYPE, product.getType());
        contentValues.put(PRODUCT_DESCRIBE, product.getDescribe());
        contentValues.put(PRODUCT_IMAGE, product.getImage());
        contentValues.put(PRODUCT_BARD_CODE
                , product.getBardCode());
        sqLiteDatabase.insert(DB_TABLE_ORDER_PRODUCT, null, contentValues);
    }

    public void updateOrderProduct(Product product) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(PRODUCT_NAME, product.getNameProduct());
        contentValues.put(PRODUCT_PRICE, product.getPrice());
        contentValues.put(PRODUCT_PRICE_IMPORT, product.getImportprice());
        contentValues.put(PRODUCT_AMOUNT, product.getAmount());
        contentValues.put(PRODUCT_TYPE, product.getType());
        contentValues.put(PRODUCT_DESCRIBE, product.getDescribe());
        contentValues.put(PRODUCT_IMAGE, product.getImage());
        contentValues.put(PRODUCT_BARD_CODE
                , product.getBardCode());
        sqLiteDatabase.update(DB_TABLE_ORDER_PRODUCT, contentValues, "ID = ?", new String[]{String.valueOf(product.getID())});
    }

    public void deleteOrderProduct() {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_ORDER_PRODUCT, null, null);
    }

    public void deleteItemOrderProduct(int ID) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_ORDER_PRODUCT, "ID = ?", new String[]{String.valueOf(ID)});
    }

    public List<Product> getAllOrderPrduct() {
        List<Product> productList = new ArrayList<>();
        Product product;
        sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(false, DB_TABLE_ORDER_PRODUCT, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(ACCOUNT_ID));
                String nameProduct = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
                long priceImport = cursor.getLong(cursor.getColumnIndex(PRODUCT_PRICE_IMPORT));
                long price = cursor.getLong(cursor.getColumnIndex(PRODUCT_PRICE));
                int amount = cursor.getInt(cursor.getColumnIndex(PRODUCT_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndex(PRODUCT_TYPE));
                String describe = cursor.getString(cursor.getColumnIndex(PRODUCT_DESCRIBE));
                String image = cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGE));
                String producer = cursor.getString(cursor.getColumnIndex(PRODUCT_BARD_CODE
                ));
                productList.add(new Product(ID, nameProduct, priceImport, price, amount, type, describe, image, producer));
            }
        return productList;
    }

    // Đây là hàm thêm báo cáo
    public void insertReport(Report report) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(REPORT_DATE, report.getDate());
        contentValues.put(REPORT_TOTAL_IMPORT, report.getTotalImport());
        contentValues.put(REPORT_TOTAL_SALE, report.getTotalSale());
        contentValues.put(REPORT_SALE_MONEY, report.getSaleMoney());
        sqLiteDatabase.insert(DB_TABLE_REPORT, null, contentValues);
    }

    // hàm get thông tin của báo cáo
    public List<Report> getAllReport() {
        List<Report> reportArrayList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(false, DB_TABLE_REPORT, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(REPORT_ID));
                long totalImport = cursor.getInt(cursor.getColumnIndex(REPORT_TOTAL_IMPORT));
                long totalSale = cursor.getLong(cursor.getColumnIndex(REPORT_TOTAL_SALE));
                String date = cursor.getString(cursor.getColumnIndex(REPORT_DATE));
                int IDEmployees = cursor.getInt(cursor.getColumnIndex(REPORT_SALE_MONEY));
                reportArrayList.add(new Report(ID, date, totalImport, totalSale, IDEmployees));
            }
        return reportArrayList;
    }

    public void insertBill(Bill bill) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
//        contentValues.put(REPORT_ID,report.getId());
        contentValues.put(BILL_DATE, bill.getDate());
        contentValues.put(BILL_NAME_PRODUCT, bill.getNames());
        contentValues.put(BILL_AMOUNT, bill.getAmount());
        contentValues.put(BILL_PRICE, bill.getPrice());
        contentValues.put(BILL_TOTAL, bill.getTotal());
        contentValues.put(BILL_ID_CUSTOMER, bill.getIDCustomer());
        contentValues.put(BILL_ID_EMPLOYEE, bill.getIDEmployee());
        sqLiteDatabase.insert(DB_TABLE_BILL, null, contentValues);
    }

    public void updateBill(Bill bill) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
//        contentValues.put(REPORT_ID,report.getId());
        contentValues.put(BILL_DATE, bill.getDate());
        contentValues.put(BILL_NAME_PRODUCT, bill.getNames());
        contentValues.put(BILL_AMOUNT, bill.getAmount());
        contentValues.put(BILL_PRICE, bill.getPrice());
        contentValues.put(BILL_TOTAL, bill.getTotal());
        contentValues.put(BILL_ID_CUSTOMER, bill.getIDCustomer());
        contentValues.put(BILL_ID_EMPLOYEE, bill.getIDEmployee());
        sqLiteDatabase.update(DB_TABLE_BILL, contentValues, BILL_ID + "=?", new String[]{String.valueOf(bill.getIDBill())});
        cursor.close();
        sqLiteDatabase.close();
    }

    public List<Bill> getAllBill() {
        List<Bill> billArrayList = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(false, DB_TABLE_BILL, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(BILL_ID));
                String date = cursor.getString(cursor.getColumnIndex(BILL_DATE));
                String nameProduct = cursor.getString(cursor.getColumnIndex(BILL_NAME_PRODUCT));
                String amount = cursor.getString(cursor.getColumnIndex(BILL_AMOUNT));
                String price = cursor.getString(cursor.getColumnIndex(BILL_PRICE));
                long total = cursor.getLong(cursor.getColumnIndex(BILL_TOTAL));
                int IDCustomer = cursor.getInt(cursor.getColumnIndex(BILL_ID_CUSTOMER));
                int IDEmployees = cursor.getInt(cursor.getColumnIndex(BILL_ID_EMPLOYEE));
                billArrayList.add(new Bill(ID, date, nameProduct, amount, price, total, IDCustomer, IDEmployees));
            }
        return billArrayList;
    }

    public void insertCustomer(Customer customer) {
        sqLiteDatabase = getWritableDatabase(); // cho phép sửa dữ liệu
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(CUSTOMER_NAME, customer.getCustomerName());
        contentValues.put(CUSTOMER_PHONE, customer.getCustomerPhone());
        contentValues.put(CUSTOMER_EMAIL, customer.getCustomerEmail());
        contentValues.put(CUSTOMER_ADDRESS, customer.getCustomerAddress());
        contentValues.put(CUSTOMER_ACCUMULATE_POINTS, customer.getCustomerPoints());
        contentValues.put(CUSTOMER_TYPE, customer.getCustomerType());
        contentValues.put(CUSTOMER_VOUCHER, customer.getCustomerVoucher());
        sqLiteDatabase.insert(DB_TABLE_CUSTOMER, null, contentValues);
        contentValues.clear();
    }

    public void insertCategory(Category category) {
        sqLiteDatabase = getWritableDatabase(); // cho phép sửa dữ liệu
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(CATEGORY_NAME, category.getNameCategory());
        contentValues.put(CATEGORY_DESCRIBE, category.getDescribe());
        contentValues.put(CATEGORY_TOTAL_PRODUCT, category.getAmountCategory());
        sqLiteDatabase.insert(DB_TABLE_CATEGORY, null, contentValues);
        contentValues.clear();
    }

    public void updateCategory(Category category) {
        sqLiteDatabase = getWritableDatabase(); // cho phép sửa dữ liệu
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(CATEGORY_NAME, category.getNameCategory());
        contentValues.put(CATEGORY_DESCRIBE, category.getDescribe());
        contentValues.put(CATEGORY_TOTAL_PRODUCT, category.getAmountCategory());
        sqLiteDatabase.update(DB_TABLE_CATEGORY, contentValues, "ID = ?", new String[]{String.valueOf(category.getIdCategory())});
        contentValues.clear();
    }

    public void deleteCategory(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_CATEGORY, "ID = ?", new String[]{String.valueOf(id)});
    }

    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        Category category;
        sqLiteDatabase = getReadableDatabase();
        try {
            // cusor là để lưu dữ liệu tạm thời
            // select
            cursor = sqLiteDatabase.query(false, DB_TABLE_CATEGORY, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(CATEGORY_ID));
                String name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
                long total = cursor.getLong(cursor.getColumnIndex(CATEGORY_TOTAL_PRODUCT));
                String describe = cursor.getString(cursor.getColumnIndex(CATEGORY_DESCRIBE));
                category = new Category(ID, name, total, describe);
                categoryList.add(category);
            }
        return categoryList;
    }

    public void updateCustomer(Customer customer) {
        sqLiteDatabase = getWritableDatabase(); // cho phép sửa dữ liệu
        contentValues = new ContentValues();
        //contentValues.put(ACCOUNT_ID, account.getID());
        contentValues.put(CUSTOMER_NAME, customer.getCustomerName());
        contentValues.put(CUSTOMER_PHONE, customer.getCustomerPhone());
        contentValues.put(CUSTOMER_EMAIL, customer.getCustomerEmail());
        contentValues.put(CUSTOMER_ADDRESS, customer.getCustomerAddress());
        contentValues.put(CUSTOMER_ACCUMULATE_POINTS, customer.getCustomerPoints());
        contentValues.put(CUSTOMER_TYPE, customer.getCustomerType());
        contentValues.put(CUSTOMER_VOUCHER, customer.getCustomerVoucher());
        sqLiteDatabase.update(DB_TABLE_CUSTOMER, contentValues, "ID = ?", new String[]{String.valueOf(customer.getIDCustomer())});
        // update = ở bảng nào , dữ liệu truyền vào, update theo cái gì ( ID ), ID truyền vào
        contentValues.clear();
    }

    public String getNameCustomer(int ID) {
        String userName = "";
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(DB_TABLE_CUSTOMER, new String[]{CUSTOMER_NAME}, CUSTOMER_ID + "= ?", new String[]{String.valueOf(ID)}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                userName += cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME));
            }
        return userName;
    }

    public void deleteCustomer(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE_CUSTOMER, "ID = ?", new String[]{String.valueOf(id)});
        //xóa = ở bảng nào, xóa theo gì (id), id truyền vào
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer;
        sqLiteDatabase = getReadableDatabase();
        try {
            // cusor là để lưu dữ liệu tạm thời
            // select
            cursor = sqLiteDatabase.query(false, DB_TABLE_CUSTOMER, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(CUSTOMER_ID));
                String userName = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(CUSTOMER_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(CUSTOMER_EMAIL));
                String address = cursor.getString(cursor.getColumnIndex(CUSTOMER_ADDRESS));
                String points = cursor.getString(cursor.getColumnIndex(CUSTOMER_ACCUMULATE_POINTS));
                String type = cursor.getString(cursor.getColumnIndex(CUSTOMER_TYPE));
                String voucher = cursor.getString(cursor.getColumnIndex(CUSTOMER_VOUCHER));
                customer = new Customer(ID, userName, phone, email, address, points, type, voucher);
                customerList.add(customer);
            }
        return customerList;
    }

    // Đây là hàm thêm phiếu nhập
    public void insertReceipt(Receipt receipt) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(RECEIPT_DATE, receipt.getDateCreate());
        contentValues.put(RECEIPT_ID_PRODUCT, receipt.getIDProduct());
        contentValues.put(RECEIPT_TOTAL_PRODUCT, receipt.getTotalProduct());
        contentValues.put(RECEIPT_ID_EMPLOYEE, receipt.getTotalProduct());
        sqLiteDatabase.insert(DB_TABLE_RECEIPT, null, contentValues);
    }

    // Đây là hàm xóa phiếu nhập với id truyền vào là id của sản phẩm
    public boolean deleteReceipt(int id)
    {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete("Receipts", RECEIPT_ID + "= ?", new String[]{String.valueOf(id)} );
        return true;
    }

    // đây là hàm get toàn bộ các phiếu nhập
    public List<Receipt> getAllReceipt() {
        List<Receipt> receiptList = new ArrayList<>();
        Receipt receipt;
        sqLiteDatabase = getReadableDatabase();
        try {
            cursor = sqLiteDatabase.query(false, DB_TABLE_RECEIPT, null, null, null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cursor != null)
            while (cursor.moveToNext()) {
                int ID = cursor.getInt(cursor.getColumnIndex(RECEIPT_ID));
                String date = cursor.getString(cursor.getColumnIndex(RECEIPT_DATE));
                String IDProduct = cursor.getString(cursor.getColumnIndex(RECEIPT_ID_PRODUCT));
                int totalProduct = cursor.getInt(cursor.getColumnIndex(RECEIPT_TOTAL_PRODUCT));
                int IDEmployee = cursor.getInt(cursor.getColumnIndex(RECEIPT_ID_EMPLOYEE));
                receipt = new Receipt(ID, date, IDProduct, totalProduct, IDEmployee);
                receiptList.add(receipt);
            }
        return receiptList;
    }
    public String getVoucher(int ID) {
        String voucher = "";
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(DB_TABLE_CUSTOMER, new String[]{CUSTOMER_VOUCHER}, CUSTOMER_ID + "= ?", new String[]{String.valueOf(ID)}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                voucher += cursor.getString(cursor.getColumnIndex(CUSTOMER_VOUCHER));
            }
        return voucher;
    }
    public Receipt getReceipt(int ID) {
        Receipt receipt = null;
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(DB_TABLE_RECEIPT, null, RECEIPT_ID + "= ?", new String[]{String.valueOf(ID)}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                int IDRe = cursor.getInt(cursor.getColumnIndex(RECEIPT_ID));
                String date = cursor.getString(cursor.getColumnIndex(RECEIPT_DATE));
                String IDProduct = cursor.getString(cursor.getColumnIndex(RECEIPT_ID_PRODUCT));
                int totalProduct = cursor.getInt(cursor.getColumnIndex(RECEIPT_TOTAL_PRODUCT));
                int IDEmployee = cursor.getInt(cursor.getColumnIndex(RECEIPT_ID_EMPLOYEE));
                 receipt = new Receipt(IDRe, date, IDProduct, totalProduct, IDEmployee);
            }
        return receipt;
    }
    public String checkPermission(int ID) {
        String permission = "";
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(DB_TABLE_ACCOUNT, new String[]{ACCOUNT_PERMISSION}, ACCOUNT_ID + "= ?", new String[]{String.valueOf(ID)}, null, null, null);
        if (cursor != null)
            while (cursor.moveToNext()) {
                permission += cursor.getString(cursor.getColumnIndex(ACCOUNT_PERMISSION));
            }
        return permission;
    }
}
