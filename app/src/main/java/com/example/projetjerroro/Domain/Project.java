package com.example.projetjerroro.Domain;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;
import com.example.projetjerroro.Domain.Enum.StatusValidationType;

import java.time.Instant;

public class Project {
    private int ID;
    private String Title;
    private String Description;
    private int Amount;
    private StatusValidationType StatusValidation;
    private String CommentValidation;
    private User Validator;
    private Instant ValidationDate;
    private ProjectStatusType Status;
    private Instant StartDate;
    private Instant EndDate;
    private User InChargeUser;

    public Project() {}

    public Project(int ID, String Title, String Description, int Amount,
                   StatusValidationType StatusValidation, String CommentValidation,
                   User Validator, Instant ValidationDate, ProjectStatusType Status,
                   Instant StartDate, Instant EndDate, User InChargeUser) {
        this.ID = ID;
        this.Title = Title;
        this.Description = Description;
        this.Amount = Amount;
        this.StatusValidation = StatusValidation;
        this.CommentValidation = CommentValidation;
        this.Validator = Validator;
        this.ValidationDate = ValidationDate;
        this.Status = Status;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.InChargeUser = InChargeUser;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public StatusValidationType getStatusValidation() {
        return StatusValidation;
    }

    public void setStatusValidation(StatusValidationType statusValidation) {
        StatusValidation = statusValidation;
    }

    public String getCommentValidation() {
        return CommentValidation;
    }

    public void setCommentValidation(String commentValidation) {
        CommentValidation = commentValidation;
    }

    public User getValidator() {
        return Validator;
    }

    public void setValidator(User validator) {
        Validator = validator;
    }

    public Instant getValidationDate() {
        return ValidationDate;
    }

    public void setValidationDate(Instant validationDate) {
        ValidationDate = validationDate;
    }

    public ProjectStatusType getStatus() {
        return Status;
    }

    public void setStatus(ProjectStatusType status) {
        Status = status;
    }

    public Instant getStartDate() {
        return StartDate;
    }

    public void setStartDate(Instant startDate) {
        StartDate = startDate;
    }

    public Instant getEndDate() {
        return EndDate;
    }

    public void setEndDate(Instant endDate) {
        EndDate = endDate;
    }

    public User getInChargeUser() {
        return InChargeUser;
    }

    public void setInChargeUser(User inChargeUser) {
        InChargeUser = inChargeUser;
    }
}
