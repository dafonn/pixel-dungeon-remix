/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.windows;

import com.nyrds.android.util.GuiProperties;
import com.nyrds.pixeldungeon.items.necropolis.BlackSkull;
import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.noosa.Text;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.Ankh;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.sprites.ItemSprite;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.Window;
import com.watabou.pixeldungeon.utils.Utils;

public class WndResurrect extends Window {
	
	private static final String TXT_MESSAGE	= Game.getVar(R.string.WndResurrect_Message);
	private static final String TXT_YES		= Game.getVar(R.string.WndResurrect_Yes);
	private static final String TXT_NO		= Game.getVar(R.string.WndResurrect_No);
	
	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 18;

	public static WndResurrect instance;
	public static Object causeOfDeath;

	public WndResurrect( final Ankh ankh, Object causeOfDeath ) {
		super();
		
		instance = this;
		WndResurrect.causeOfDeath = causeOfDeath;
		
		IconTitle titlebar = new IconTitle();
		if (ankh != null){
			titlebar.icon( new ItemSprite( ankh ) );
			titlebar.label( ankh.name() );
		} else {
			titlebar.icon( new ItemSprite( new BlackSkull()) );
			titlebar.label( Utils.capitalize( Game.getVar(R.string.Necromancy_Title) ) );
		}

		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		Text message = PixelScene.createMultiline( TXT_MESSAGE, GuiProperties.regularFontSize() );
		message.maxWidth(WIDTH);
		message.measure();
		message.y = titlebar.bottom() + GAP;
		add( message );
		
		RedButton btnYes = new RedButton( TXT_YES ) {
			@Override
			protected void onClick() {
				hide();
				
				Statistics.ankhsUsed++;
				
				InterlevelScene.mode = InterlevelScene.Mode.RESURRECT;
				Game.switchScene( InterlevelScene.class );
			}
		};
		btnYes.setRect( 0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnYes );
		
		RedButton btnNo = new RedButton( TXT_NO ) {
			@Override
			protected void onClick() {
				hide();
				Hero.reallyDie( WndResurrect.causeOfDeath );
			}
		};
		btnNo.setRect( 0, btnYes.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add( btnNo );
		
		resize( WIDTH, (int)btnNo.bottom() );
	}
	
	@Override
	public void destroy() {
		super.destroy();
		instance = null;
	}
	
	@Override
	public void onBackPressed() {
	}
}
