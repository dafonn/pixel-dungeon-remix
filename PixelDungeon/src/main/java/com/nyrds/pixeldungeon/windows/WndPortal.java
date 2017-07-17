package com.nyrds.pixeldungeon.windows;

import com.nyrds.android.util.GuiProperties;
import com.nyrds.pixeldungeon.items.common.Library;
import com.nyrds.pixeldungeon.levels.objects.PortalGate;
import com.nyrds.pixeldungeon.ml.R;
import com.nyrds.pixeldungeon.utils.Position;
import com.watabou.noosa.Game;
import com.watabou.noosa.Text;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.TextButton;
import com.watabou.pixeldungeon.ui.Window;

import java.util.HashMap;
import java.util.Map;

public class WndPortal extends Window {

	private static final int BTN_HEIGHT	= 18;
	private static final int BTN_WIDTH	= 38;
	private static final int WIDTH		= 100;
	private static final int GAP		= 2;

	private static final String TXT_TITLE = Game.getVar(R.string.WndPortal_Title);
	private static final String TXT_INFO = Game.getVar(R.string.WndPortal_Info);
	private static final String BTN_YES = Game.getVar(R.string.Wnd_Button_Yes);
	private static final String BTN_NO = Game.getVar(R.string.Wnd_Button_No);

	public WndPortal(final PortalGate portal, final Hero hero, final Position returnTo ) {
		super();

		//Title text
		Text tfTitle = PixelScene.createMultiline(TXT_TITLE, GuiProperties.mediumTitleFontSize());
		tfTitle.hardlight(TITLE_COLOR);
		tfTitle.maxWidth(WIDTH - GAP);
		tfTitle.measure();
		tfTitle.x = (WIDTH - tfTitle.width())/2;
		tfTitle.y = GAP;
		add(tfTitle);

		//Instruction text
		Text message = PixelScene.createMultiline(TXT_INFO, GuiProperties.regularFontSize() );
		message.maxWidth(WIDTH);
		message.measure();
		message.y = tfTitle.bottom()+ GAP;
		add( message );

		int buttonY = (int) message.bottom()+ GAP;


		//Yes Button
		TextButton btnYes = new RedButton(BTN_YES) {
			@Override
			protected void onClick() {
				super.onClick();
				hide();
				portal.useUp();

				hero.setPortalLevelCoordinates(Dungeon.currentPosition());
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnTo = new Position(returnTo);
				Game.switchScene( InterlevelScene.class );
			}
		};

		btnYes.setRect((WIDTH / 2) - (BTN_WIDTH / 2) , BTN_HEIGHT / 2 + GAP * 2 + buttonY, BTN_WIDTH, BTN_HEIGHT);
		add(btnYes);

		buttonY = (int) btnYes.bottom()+ GAP;

		//No Button
		TextButton btnNo = new RedButton(BTN_NO) {
			@Override
			protected void onClick() {
				super.onClick();
				hide();
			}
		};

		btnNo.setRect((WIDTH / 2) - (BTN_WIDTH / 2) , GAP * 2 + buttonY, BTN_WIDTH, BTN_HEIGHT);
		add(btnNo);

		resize( WIDTH, (int) btnNo.bottom() + BTN_HEIGHT / 2);
	}
}
