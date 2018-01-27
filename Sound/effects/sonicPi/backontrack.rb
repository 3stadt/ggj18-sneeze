# treshold warning


define :warning do
  start_tone = 70
  3.times do
    with_fx :lpf do
      with_fx :reverb do
        play start_tone, release:0.2 , pan:rrand(-1,0)
        sleep 0.1
        play start_tone-1, release:0.2, pan:rrand(-0.5,0.5)
        sleep 0.1
        play start_tone-2, release:0.2, pan:rrand(0,1)
        sleep 0.1
      end
    end
  end
end


define :backontrack do
  start_tone = 70
  with_fx :lpf do
    with_fx :reverb do
      play start_tone, release: 0.3, pan:rrand(-1,0), amp:0.1
      sleep 0.25
      play start_tone+3, release: 0.3, pan:rrand(-0.5,0.5), amp:0.2
      sleep 0.1
      play start_tone+5, release: 0.3, pan:rrand(0,1), amp:0.2, amp:0.3
      sleep 0.25
      play start_tone+7, release: 0.6, pan:rrand(-1,1), amp:0.4
      sleep 0.5
      play start_tone+12, release: 0.6, amp:1
    end
  end
end



backontrack

