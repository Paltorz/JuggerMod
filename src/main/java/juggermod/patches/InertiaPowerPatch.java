package juggermod.patches;

/*
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction", method="update")
public class InertiaPowerPatch {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("hasRelic")) {
                    // pass the original argument (relicID) + this ($0 which is the AbstractCard)
                    m.replace("{ $_ = juggermod.JuggerMod.hasRelicCustomI($1); }");
                }
            }
        };
    }
}
*/