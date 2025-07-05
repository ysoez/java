package adapter.mail;

import adapter.mail.gmail.GmailClient;

public class GmailAdapter implements EmailProvider {

    private GmailClient client = new GmailClient();

    @Override
    public void downloadEmails() {
        client.connect();
        client.getEmails();
        client.disconnect();
    }

}
