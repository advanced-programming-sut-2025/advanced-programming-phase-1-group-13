package models.enums.commands;

public enum ProfileCommands implements Command {
    CHANGE_USERNAME("^//s+change//s+username//s+-u//s+(?<username>[a-zA-Z0-9-]+)$"),
    CHANGE_NICKNAME("^//s+change//s+nickname//s+-u//s+(?<nickname>[a-zA-Z0-9-]+)$"),
    CHANGE_EMAIL("^//s+change//s+email//s+(?<email>(?!.*\\\\.\\\\.)[A-Za-z0-9](?:[A-Za-z0-9._-]*[A-Za-z0-9])?@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\\\.)+[A-Za-z]{2,})$"),
    CHANGE_PASSWORD("^//s+change$")



    }
