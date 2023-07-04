CREATE TABLE IF NOT EXISTS BANK_ACCOUNT(
        id UUID,
        PRIMARY KEY (id),
        account CHAR(10),
        agency CHAR(4),
        bank_code CHAR(3),
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS CARD_HOLDER(
        id UUID,
        PRIMARY KEY (id),
        client_id UUID NOT NULL UNIQUE,
        credit_analysis_id UUID NOT NULL UNIQUE,
        status VARCHAR(8) NOT NULL,
        credit_limit DOUBLE PRECISION NOT NULL,
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP NOT NULL,
        bank_account_id UUID UNIQUE,
        CONSTRAINT fk_bank_account_id FOREIGN KEY (bank_account_id) REFERENCES BANK_ACCOUNT (id)
);

CREATE TABLE IF NOT EXISTS CARD(
        id UUID,
        PRIMARY KEY (id),
        credit_limit DOUBLE PRECISION NOT NULL,
        card_number VARCHAR(19) NOT NULL,
        cvv INTEGER NOT NULL,
        due_date DATE NOT NULL,
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP NOT NULL,
        card_holder_id UUID NOT NULL,
        CONSTRAINT fk_card_holder_id FOREIGN KEY (card_holder_id) REFERENCES CARD_HOLDER (id)
);

