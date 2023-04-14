package template;

class GenerateReport extends Task {

    GenerateReport(AuditTrail audit) {
        super(audit);
    }

    @Override
    protected void doExecute() {
        System.out.println("Generate report");
    }

}
