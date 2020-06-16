package com.example.projetjerroro.Service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;
import com.example.projetjerroro.Domain.Enum.StatusValidationType;
import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Domain.Role;
import com.example.projetjerroro.Domain.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtilsService {

    public static  String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Project getProjectFromJson(JSONObject obj) throws JSONException {
        System.out.println(obj);
        if (obj != null) {
            Project project = new Project();
            project.setID(obj.getInt("ID"));
            project.setTitle(obj.getString("Title"));
            project.setDescription(obj.getString("Description"));
            project.setAmount(obj.getInt("Amount"));
            project.setStatusValidation(StatusValidationType.valueOf(obj.getString("StatusValidation")));
            project.setCommentValidation(obj.getString("CommentValidation"));
            if (obj.getString("Validator") != "null" && obj.getString("Validator") != null) {
                project.setValidator(UtilsService.getUserFromJson(obj.getJSONObject("Validator")));
            }
            project.setValidationDate(getDateFromString(obj.getString("ValidationDate")));
            project.setStatus(ProjectStatusType.valueOf(obj.getString("Status")));
            project.setStartDate(getDateFromString(obj.getString("StartDate")));
            project.setEndDate(getDateFromString(obj.getString("EndDate")));
            project.setInChargeUser(getUserFromJson(obj.getJSONObject("InChargeUser")));
            return project;
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Instant getDateFromString(String obj) {
        String timestamp = obj;
        if (timestamp != "null" && timestamp != null) {
            LocalDate date = LocalDate.parse(timestamp);
            return date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        return null;
    }

    private static User getUserFromJson(JSONObject obj) throws JSONException {
        if (obj != null) {
            User user = new User();
            user.setID(obj.getInt("ID"));
            user.setLogin(obj.getString("Login"));
            user.setEmail(obj.getString("Email"));
            user.setImageUrl(obj.getString("ImageUrl"));
            user.setFirstName(obj.getString("FirstName"));
            user.setLastName(obj.getString("LastName"));
            List<Role> roles = new ArrayList<>();
            JSONArray jsonArray = obj.getJSONArray("Roles");
            for (int i = 0; i < jsonArray.length(); i++) {
                roles.add(getRoleFromJson(jsonArray.getJSONObject(i)));
            }
            user.setRoles(roles);
            return user;
        }
        return null;
    }

    private static Role getRoleFromJson(JSONObject obj) throws JSONException {
        if (obj != null) {
            return new Role(obj.getInt("ID"), obj.getString("Name"));
        }
        return null;
    }
}
