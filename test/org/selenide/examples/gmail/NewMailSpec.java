package org.selenide.examples.gmail;

import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static org.selenide.examples.gmail.Highlighter.highlight;

public class NewMailSpec extends GmailTests {
  @Test
  public void userCanComposeEmail() {
    $(byText("Schreiben")).click();
    waitUntilPagesIsLoaded();
    $(By.name("to")).val("marc@schanne.org").pressTab();
    $(by("placeholder", "Betreff")).val("GMail-Test").pressTab();

    $(".editable").val("Text.").pressEnter();
    $(byText("Senden")).click();

    $(withText("Die Nachricht wurde gesendet.")).shouldBe(visible);
    $(byText("Rückgängig")).click();
    highlight($(byText("Das Senden wurde rückgängig gemacht.")).should(appear));

    $(".editable").should(appear)
        .append("Hello from Selenide")
        .pressEnter()
        .pressEnter();

    $(byText("Senden")).click();
    highlight($(withText("Die Nachricht wurde gesendet.")).should(appear));
    highlight($(byText("Rückgängig")).should(appear)).waitUntil(disappears, 12000);

    assertUserCanSeeSentEmails();
  }

  private void assertUserCanSeeSentEmails() {
    $(byText("Nachricht ansehen")).click();
    highlight($(byText("GMail-Test")).shouldBe(visible));
  }
}
