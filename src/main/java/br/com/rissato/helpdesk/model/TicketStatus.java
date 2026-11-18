public enum TicketStatus {
    OPEN(true),
    IN_PROGRESS(true),
    WAITING_FOR_USER(true),
    WAITING_IN_QUEUE(true),
    CANCELED(false),
    RESOLVED(false),
    CLOSED(false);

    private final boolean open;

    TicketStatus(boolean open){
        this.open = open;
    }

    public boolean isOpen(){
        return open;
    }
}