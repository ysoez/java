package template;

class MoneyTransfer extends Task {

    MoneyTransfer(AuditTrail audit) {
        super(audit);
    }

    @Override
    protected void doExecute() {
        System.out.println("Money transfer");
    }

}
