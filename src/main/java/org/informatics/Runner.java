package org.informatics;

import org.informatics.entity.*;
import org.informatics.exception.*;
import org.informatics.service.contract.CashdeskService;
import org.informatics.service.contract.GoodsService;
import org.informatics.service.contract.ReceiptService;
import org.informatics.service.contract.StoreService;
import org.informatics.service.impl.CashdeskServiceImpl;
import org.informatics.service.impl.GoodsServiceImpl;
import org.informatics.service.impl.ReceiptServiceImpl;
import org.informatics.service.impl.StoreServiceImpl;
import org.informatics.util.GoodsType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Runner {

    static Store store;
    static Cashdesk cashdesk;
    static Employee currEmployee;
    static Client client;

    static ReceiptService receiptService = new ReceiptServiceImpl();
    static StoreServiceImpl storeService = new StoreServiceImpl();
    static CashdeskServiceImpl cashdeskService = new CashdeskServiceImpl(receiptService);
    static GoodsService goodsService = new GoodsServiceImpl();

    static List<Goods> deliveredGoods = new ArrayList<>();
    static Map<Goods, BigDecimal> clientMap = new HashMap<>();
    static Map<Goods, BigDecimal> scannedGoodsList = new HashMap();
    static List<Store> storeList = new ArrayList<>();


    // Store init
    static List<Employee> employeeList = new ArrayList<>();
    static List<Cashdesk> cashdeskList = new ArrayList<>();
    static List<Client> clientList = new ArrayList<>();
    static List<Receipt> receiptList = new ArrayList<>();

    public static void printInfo(){
        System.out.println("1. Deliver objects to store");
        System.out.println("2. Scan goods of a client");
        System.out.println("3. ");
        System.out.println("4. Get total revenue of receipts");
        System.out.println("5. Total Expenses for salaries in the store");
        System.out.println("6. Total Revenue of the store");
        System.out.println("7. Total Profit of the store");
        System.out.println("8. Count of receipts");
        System.out.println("9. ");
        System.out.println("10. Hire Employee");
        System.out.println("11. Create a store");
        System.out.println("12. All employees info");
        System.out.println("13. Open a cashdesk");
        System.out.println("14. All stores info");
        System.out.println("0. Exit");
    }
    public static void RunApplication() {
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (isRunning) {

            printInfo();

            int choice = scanner.nextInt();

            switch(choice){
                case 0:
                    isRunning = false;
                    break;
                case 1:
                    System.out.println("Which store: 1 - " + storeList.size());
                    for(int i = 0; i < storeList.size(); i++){
                        System.out.println((i+1) + " - " + storeList.get(i).getName());
                    }

                    int storeChoiceDeliver = scanner.nextInt()-1;

                    System.out.println("Enter goodsName of a client");
                    String goodsName = scanner.next();

                    System.out.println("Enter goods manufacturer price");
                    BigDecimal manPrice = scanner.nextBigDecimal();

                    System.out.println("Enter goodsType | NON_FOODS/GROCERIES");
                    GoodsType goodsType = GoodsType.valueOf(scanner.next());

                    System.out.println("Enter a date (yyyy-MM-dd)");
                    String date = scanner.next();
                    LocalDate localDate = LocalDate.parse(date);

                    System.out.println("Enter quantity");
                    BigDecimal quantity = scanner.nextBigDecimal();


                    Goods goods = new Goods(goodsName, manPrice, goodsType, localDate);

                    try {
                        storeService.deliverGoods(storeList.get(storeChoiceDeliver), goods, quantity, deliveredGoods);
                    } catch (NotValidArgumentException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(storeList.get(storeChoiceDeliver).getName());
                    break;

                case 2:
                    System.out.println("Which store: 1 - " + storeList.size());
                    for(int i = 0; i < storeList.size(); i++){
                        System.out.println((i+1) + " - " + storeList.get(i).getName());
                    }
                    int storeChoice = scanner.nextInt()-1;

                    System.out.println("Which employee: 1 - " + employeeList.size());
                    for(int i = 0; i < employeeList.size(); i++){
                        System.out.println((i+1) + " - " + employeeList.get(i).getName());
                    }
                    int employeeChoice = scanner.nextInt()-1;

                    System.out.println("Which client: 1 - " + clientList.size());
                    for(int i = 0; i < clientList.size(); i++){
                        System.out.println((i+1) + " - " + clientList.get(i));
                    }
                    int clientChoice = scanner.nextInt()-1;

                    System.out.println("Client Goods List");
                    System.out.println(storeList.get(storeChoice).getDeliveredGoods());

                    System.out.println("Choose goods for client to buy");

                    for(int i = 0; i < deliveredGoods.size(); i++){
                        System.out.println((i+1) + " - " + deliveredGoods.get(i).getName());
                    }

                    int choiceGoodsClient = scanner.nextInt()-1;

                    System.out.println("Quantity:");
                    BigDecimal quantityClientGoods = scanner.nextBigDecimal();

                    clientMap.put(deliveredGoods.get(choiceGoodsClient), quantityClientGoods);
                    client.setGoodsToBuy(clientMap);

                    try {
                        cashdeskService.performOperationOnCashdesk(storeList.get(storeChoice), employeeList.get(employeeChoice), clientList.get(clientChoice));
                    } catch (ExpiredGoodsException e) {
                        throw new RuntimeException(e);
                    } catch (NotEnoughQuantityException e) {
                        throw new RuntimeException(e);
                    } catch (NotEnoughBudgetException e) {
                        throw new RuntimeException(e);
                    } catch (NotValidArgumentException e) {
                        throw new RuntimeException(e);
                    } catch (NotAvailableCashdeskException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(scannedGoodsList);
                    break;
                case 3:

                    break;
                    case 4:
                        System.out.println("Which store: 1 - " + storeList.size());
                        for(int i = 0; i < storeList.size(); i++){
                            System.out.println((i+1) + " - " + storeList.get(i).getName());
                        }
                        int storeChoiceReceiptRevenue = scanner.nextInt()-1;

                        try {
                            System.out.println(receiptService.getTotalAmountSoFar(storeList.get(storeChoiceReceiptRevenue)));
                        } catch (ReceiptsListIsEmptyException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                case 5:
                    System.out.println("Which store: 1 - " + storeList.size());
                    for(int i = 0; i < storeList.size(); i++){
                        System.out.println((i+1) + " - " + storeList.get(i).getName());
                    }

                    int storeChoiceSalaries = scanner.nextInt()-1;
                    try {
                        System.out.println(storeService.getTotalSalaries(storeList.get(storeChoiceSalaries)));
                    } catch (EmployeeListEmptyException e) {
                        throw new RuntimeException(e);
                    } catch (NotValidArgumentException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 6:
                    System.out.println("Which store: 1 - " + storeList.size());
                    for(int i = 0; i < storeList.size(); i++){
                        System.out.println((i+1) + " - " + storeList.get(i).getName());
                    }
                    int storeChoiceStoreRevenue = scanner.nextInt()-1;

                    try {
                        System.out.println(storeService.getTotalRevenue(storeList.get(storeChoiceStoreRevenue), goodsService));
                    } catch (NotValidArgumentException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 7:
                    System.out.println("Which store: 1 - " + storeList.size());
                    for(int i = 0; i < storeList.size(); i++){
                        System.out.println((i+1) + " - " + storeList.get(i).getName());
                    }
                    int storeChoiceStoreProfit = scanner.nextInt()-1;

                    try {
                        System.out.println(storeService.getTotalProfit(storeList.get(storeChoiceStoreProfit), goodsService));
                    } catch (NotValidArgumentException e) {
                        throw new RuntimeException(e);
                    } catch (StoreDeliveredGoodsEmptyException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 8:
                    System.out.println("Which store: 1 - " + storeList.size());
                    for(int i = 0; i < storeList.size(); i++){
                        System.out.println((i+1) + " - " + storeList.get(i).getName());
                    }
                    int storeChoiceReceiptsCount = scanner.nextInt()-1;

                    System.out.println(receiptService.getTotalCountSoFar(storeList.get(storeChoiceReceiptsCount)));
                    break;
                case 9:
                    break;
                case 10:
                        System.out.println("Enter employee name");
                        String employeeName = scanner.next();
                        System.out.println("Employee salary");
                        BigDecimal salary = scanner.nextBigDecimal();
                        currEmployee = new Employee(employeeName, salary);
                        employeeList.add(currEmployee);
                        System.out.println("Employee added" + currEmployee.toString());
                        break;
                        case 11:
                            System.out.println("Enter store name");
                            String storeName = scanner.next();
                            System.out.println("Enter groceries surcharge");
                            BigDecimal groceriesSurcharge = scanner.nextBigDecimal();
                            System.out.println("Enter non food surcharge");
                            BigDecimal nonFoodSurcharge = scanner.nextBigDecimal();
                            System.out.println("Enter days for sale");
                            int daysForSaleInput = scanner.nextInt();
                            System.out.println("Percentage for sale");
                            double percentage = scanner.nextDouble();
                            store = new Store(storeName, employeeList, cashdeskList, groceriesSurcharge, nonFoodSurcharge, daysForSaleInput, percentage);
                            storeList.add(store);
                            System.out.println(storeList);
                            break;
                    case 12:
                        System.out.println(employeeList);
                        break;
                case 13:
                    System.out.println("Enter budget for next client: ");
                    BigDecimal clientBudget = scanner.nextBigDecimal();
                    client = new Client(clientBudget);
                    clientList.add(client);
                    cashdesk = new Cashdesk(currEmployee);
                    cashdeskList.add(cashdesk);
                    System.out.println(cashdesk);
                    break;
                case 14:
                    System.out.println(storeList);
                    break;
                default:
                    System.out.println("Invalid input. Try again (0-14)");
                    break;
            }
        }
    }
}
